package pl.agh.to.lang.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.agh.to.lang.model.Flashcard;
import pl.agh.to.lang.model.Sentence;

import java.util.List;

@AllArgsConstructor
@Getter
public class FlashcardsResponse {
    private final Sentence sentence;

    private final List<Flashcard> flashcards;
}
