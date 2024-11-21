package app.lyrix.backend.controller;

import app.lyrix.backend.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/songs")
public class CustomSongController {
    @Autowired
    private SongService songService;

    @GetMapping("/{id}/transcriptions")
    public String getTranscription(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "en") String language) {
        return songService.getOrGenerateTranscription(id, language);
    }
}
