package pl.agh.to.lang.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Flashcard {
    @NotBlank
    private final String word;

    @NotNull
    private String translation = "";

    public Flashcard(String word) {
        if (word.isBlank()) {
            throw new IllegalArgumentException("Blank word");
        }

        this.word = word;
    }
}
