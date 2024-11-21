package app.lyrix.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor  // Lombok: No-arg constructor required by JPA
@AllArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Lombok: Getter for id
    private int id;

    @Setter
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    @Setter  // Lombok: Getter and Setter for author
    private Author author;

    @Getter @Setter @Column(columnDefinition = "TEXT")  // Store large text data
    public String hindiLyrics;
    @Getter @Setter @Column(columnDefinition = "TEXT")  // Store large text data
    public String urduLyrics;
    @Getter @Setter @Column(columnDefinition = "TEXT")  // Store large text data
    public String englishLyrics;

    @Getter @Setter @Column(columnDefinition = "TEXT")  // Store large text data
    public String hindiAnalysis;
    @Getter @Setter @Column(columnDefinition = "TEXT")  // Store large text data
    public String urduAnalysis;
    @Getter @Setter @Column(columnDefinition = "TEXT")  // Store large text data
    public String englishAnalysis;

    // Override equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id == song.id &&
                Objects.equals(title, song.title) &&
                Objects.equals(author, song.author);
    }

    // Override hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(id, title, author);
    }

    // Override toString for better logging and debugging
    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author=" + (author != null ? author.getName() : "null") +
                '}';
    }
}
