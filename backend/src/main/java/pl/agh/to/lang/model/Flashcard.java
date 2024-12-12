package pl.agh.to.lang.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Flashcard {
    @NotBlank
    private final String word;

    @NotBlank
    private String lemma;

    @NotNull
    private String partOfSpeech = "";

    @NotNull
    private List<String> inflections = new ArrayList<>();

    public Flashcard(String word) {
        if (word.isBlank()) {
            throw new IllegalArgumentException("Blank word");
        }

        this.word = word;
    }
}
