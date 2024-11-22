package app.lyrix.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "line_analysis")
public class LineAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "song_id", referencedColumnName = "id", nullable = false)
    @Setter // Lombok: Getter and Setter for author
    private Song song;

    @Column(nullable = false)
    private int lineNumber;

    @Column(nullable = false)
    private String language;

    @Column(columnDefinition = "TEXT")
    private String analysis;
}
