package app.lyrix.backend.service;

import app.lyrix.backend.model.LineAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.lyrix.backend.model.Song;
import app.lyrix.backend.repository.LineAnalysisRepository;
import app.lyrix.backend.repository.SongRepository;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private LineAnalysisRepository lineAnalysisRepository;

    @Autowired
    private GPTService gptService;

    public String getSongSummary(int songid, String language){
        Song song = songRepository.findById(songid)
                .orElseThrow(() -> new RuntimeException("Song not found"));
        String theme = getThemeByLanguage(song, language);

        if (theme == null){
            String newTheme = gptService.generateTheme(getLyricsByLanguage(song, language), language);
            switch (language) {
                case "en" -> song.setEnglishTheme(newTheme);
                case "hin" -> song.setHindiTheme(newTheme);
                case "urd" -> song.setUrduTheme(newTheme);
                default -> throw new IllegalArgumentException("Invalid language code: " + language);
            }
            songRepository.save(song);
            return newTheme;
        }
        return theme;
    }

    public String getOrGenerateLineTranscription(int songid, int lineNumber, String language) {
         Song song = songRepository.findById(songid)
                 .orElseThrow(() -> new RuntimeException("Song not found"));

         LineAnalysis analysis =
         lineAnalysisRepository.findBySongAndLineNumberAndLanguage(song, lineNumber, language);

         if (analysis == null) {
             String lyrics = getLyricsByLanguage(song, language);
             String line = lyrics.split("\n")[lineNumber];
             String generatedWork = gptService.generateAnalysis(lyrics, line, language);
             if (generatedWork.endsWith("`")){
                 generatedWork = generatedWork.substring(4, generatedWork.length()-3);
             }
             LineAnalysis newAnalysis = new LineAnalysis();
             newAnalysis.setSong(song); // This sets the song_id column in the database
             newAnalysis.setLineNumber(lineNumber);
             newAnalysis.setLanguage(language);
             newAnalysis.setAnalysis(generatedWork);

             // Save the new analysis to the database
             lineAnalysisRepository.save(newAnalysis);
             return newAnalysis.getAnalysis();
        }
        return analysis.getAnalysis();

    }

    private String getLyricsByLanguage(Song song, String language) {
        switch (language) {
            case "en" -> {
                return song.getEnglishLyrics();
            }
            case "hin" -> {
                return song.getHindiLyrics();
            }
            case "urd" -> {
                return song.getUrduLyrics();
            }
            default -> throw new IllegalArgumentException("Invalid language code: " + language);
        }
    }

    private String getThemeByLanguage(Song song, String language) {
        switch (language) {
            case "en" -> {
                return song.getEnglishTheme();
            }
            case "hin" -> {
                return song.getHindiTheme();
            }
            case "urd" -> {
                return song.getUrduTheme();
            }
            default -> throw new IllegalArgumentException("Invalid language code: " + language);
        }
    }

}
