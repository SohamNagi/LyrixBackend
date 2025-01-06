package app.lyrix.backend.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
// No-arg constructor required by JPA
// Optional: Generates an all-args constructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songList;

    public Author(int id, String name, List<Song> songList) {
        this.id = id;
        this.name = name;
        this.songList = songList;
    }

    public Author() {
    }

    // Optional: Additional custom logic (if needed)
    public void addSong(Song song) {
        song.setAuthor(this);
        songList.add(song);
    }

    // Override toString for better logging and debugging
    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    // Override equals and hashCode for entity comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id && Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Song> getSongList() {
        return this.songList;
    }

    public void setName(String name) {
        this.name = name;
    }
}
