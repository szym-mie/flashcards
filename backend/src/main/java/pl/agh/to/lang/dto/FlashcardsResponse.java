package pl.agh.to.lang.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import pl.agh.to.lang.model.Flashcard;
import pl.agh.to.lang.model.Sentence;

import java.util.List;

@AllArgsConstructor
@Value
public class FlashcardsResponse {
    private final Sentence sentence;

    private final List<Flashcard> flashcards;
}
