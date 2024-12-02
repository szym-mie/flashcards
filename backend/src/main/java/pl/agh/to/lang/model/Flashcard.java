package pl.agh.to.lang.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Flashcard {
    private String word;

    private String translation;
}
