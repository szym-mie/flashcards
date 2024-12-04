package pl.agh.to.lang.model;

import lombok.Data;

@Data
public class Flashcard {
    private final String word;

    private String translation = "";

    public Flashcard(String word) {
        if (word.isBlank()) {
            throw new IllegalArgumentException("Blank word");
        }

        this.word = word;
    }
}
