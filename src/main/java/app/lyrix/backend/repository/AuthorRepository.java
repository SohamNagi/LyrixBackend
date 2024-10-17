package app.lyrix.backend.repository;

import app.lyrix.backend.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "https://www.lyrix.app")
@RepositoryRestResource(path = "authors", collectionResourceRel = "authors", itemResourceRel = "author")
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    // Custom query example
    Author findByName(String name);
}