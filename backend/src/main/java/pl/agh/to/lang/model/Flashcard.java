package pl.agh.to.lang.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Flashcard {
    private String word;

    private String translation;

    public Flashcard(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }
}
