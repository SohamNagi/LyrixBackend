package app.lyrix.backend.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
// Lombok: No-arg constructor required by JPA
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Lombok: Getter for id
    private int id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    // Lombok: Getter and Setter for author
    private Author author;

    @Column(columnDefinition = "TEXT") // Store large text data
    public String hindiLyrics;
    @Column(columnDefinition = "TEXT") // Store large text data
    public String urduLyrics;
    @Column(columnDefinition = "TEXT") // Store large text data
    public String englishLyrics;

    @Column(columnDefinition = "TEXT") // Store large text data
    public String hindiTheme;
    @Column(columnDefinition = "TEXT") // Store large text data
    public String urduTheme;
    @Column(columnDefinition = "TEXT") // Store large text data
    public String englishTheme;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineAnalysis> analyses;

    public Song(int id, String title, Author author, String hindiLyrics, String urduLyrics, String englishLyrics, String hindiTheme, String urduTheme, String englishTheme, List<LineAnalysis> analyses) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.hindiLyrics = hindiLyrics;
        this.urduLyrics = urduLyrics;
        this.englishLyrics = englishLyrics;
        this.hindiTheme = hindiTheme;
        this.urduTheme = urduTheme;
        this.englishTheme = englishTheme;
        this.analyses = analyses;
    }

    public Song() {
    }

    // Override equals method
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
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

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public Author getAuthor() {
        return this.author;
    }

    public List<LineAnalysis> getAnalyses() {
        return this.analyses;
    }

    public String getHindiLyrics() {
        return this.hindiLyrics;
    }

    public String getUrduLyrics() {
        return this.urduLyrics;
    }

    public String getEnglishLyrics() {
        return this.englishLyrics;
    }

    public String getHindiTheme() {
        return this.hindiTheme;
    }

    public String getUrduTheme() {
        return this.urduTheme;
    }

    public String getEnglishTheme() {
        return this.englishTheme;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setHindiLyrics(String hindiLyrics) {
        this.hindiLyrics = hindiLyrics;
    }

    public void setUrduLyrics(String urduLyrics) {
        this.urduLyrics = urduLyrics;
    }

    public void setEnglishLyrics(String englishLyrics) {
        this.englishLyrics = englishLyrics;
    }

    public void setHindiTheme(String hindiTheme) {
        this.hindiTheme = hindiTheme;
    }

    public void setUrduTheme(String urduTheme) {
        this.urduTheme = urduTheme;
    }

    public void setEnglishTheme(String englishTheme) {
        this.englishTheme = englishTheme;
    }
}
