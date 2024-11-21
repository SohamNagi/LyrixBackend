package app.lyrix.backend.service;


import app.lyrix.backend.model.Song;
import app.lyrix.backend.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private GPTService gptService;

    public String getOrGenerateTranscription(int id, String language){
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found"));
        if (Objects.equals(language, "en")){
            if (song.getEnglishAnalysis() == null){
                /// GPT FETCHING
                song.setEnglishAnalysis("English Analysis");
                songRepository.save(song);
            }
            return song.getEnglishAnalysis();
        } else if (Objects.equals(language, "hin")){
            if (song.getHindiAnalysis() == null){
                /// GPT FETCHING
                song.setHindiAnalysis("Hindi Analysis");
                songRepository.save(song);

            }
            return song.getHindiAnalysis();

        } else if (Objects.equals(language, "urd")){
            if (song.getUrduAnalysis() == null){
                /// GPT FETCHING
                song.setUrduAnalysis("Urdu Analysis");
                songRepository.save(song);
            }
            return song.getUrduAnalysis();

        } else {
            throw new IllegalArgumentException("Invalid language code: " + language);
        }
    }
}
