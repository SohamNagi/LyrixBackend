package app.lyrix.backend.repository;

import app.lyrix.backend.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4000")
@RepositoryRestResource(path = "songs", collectionResourceRel = "songs", itemResourceRel = "song")
public interface SongRepository extends JpaRepository<Song, Integer> {
    // Find all songs by a specific author's ID
    List<Song> findByAuthorId(int authorId);

    List<Song> findByTitleContainingIgnoreCase(String title);  // Case-insensitive partial match
}
