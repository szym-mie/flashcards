package pl.agh.to.lang.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.agh.to.lang.helpers.PartOfSentence;
import pl.agh.to.lang.helpers.PartOfSpeech;

@Data
public class Flashcard {
    @NotBlank
    private final String word;

    @NotNull
    private String lemma = ""; // base form

    @NotNull
    private String translation = "";

    @NotNull
    private String transcription = "";

    @NotNull
    private PartOfSpeech partOfSpeech = null;

    @NotNull
    private PartOfSentence partOfSentence = null;
    
    public Flashcard(String word) {
        if (word.isBlank()) {
            throw new IllegalArgumentException("Blank word");
        }
        this.word = word;
    }
}
