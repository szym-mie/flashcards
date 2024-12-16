package pl.agh.to.lang.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@IdClass(LemmaId.class)
public class Lemma {
    @Id
    @NotBlank
    private String name;

    @NotBlank
    private String translation;

    @Id
    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;
}
