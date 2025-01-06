package app.lyrix.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "line_analysis")
public class LineAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "song_id", referencedColumnName = "id", nullable = false)
    // Lombok: Getter and Setter for author
    private Song song;

    @Column(nullable = false)
    private int lineNumber;

    @Column(nullable = false)
    private String language;

    @Column(columnDefinition = "TEXT")
    private String analysis;

    public LineAnalysis(int id, Song song, int lineNumber, String language, String analysis) {
        this.id = id;
        this.song = song;
        this.lineNumber = lineNumber;
        this.language = language;
        this.analysis = analysis;
    }

    public LineAnalysis() {
    }

    public int getId() {
        return this.id;
    }

    public Song getSong() {
        return this.song;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getAnalysis() {
        return this.analysis;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
