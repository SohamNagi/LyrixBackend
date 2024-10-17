package app.lyrix.backend.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    private String name;

    // Optional: One-to-Many if you want a bidirectional relationship
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songList;
}
