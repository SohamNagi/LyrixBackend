package app.lyrix.backend.repository;

import app.lyrix.backend.model.LineAnalysis;
import app.lyrix.backend.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LineAnalysisRepository extends JpaRepository<LineAnalysis, Long> {
    LineAnalysis findBySongAndLineNumberAndLanguage(Song song, int lineNumber, String language);
}
