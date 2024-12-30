package app.lyrix.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import app.lyrix.backend.service.SongService;

@CrossOrigin(origins = "http://localhost:4000")
@RestController
@RequestMapping("/api/songs")
public class CustomSongController {
    @Autowired
    private SongService songService;

    @GetMapping("/{id}/transcription")
    public String getTranscription(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "en") String language,
            @RequestParam() int linenum) {
        return songService.getOrGenerateLineTranscription(id, linenum, language);
    }

    @GetMapping("/{id}/theme")
    public String getTheme(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "en") String language) {
        return songService.getSongSummary(id, language);
    }
}
