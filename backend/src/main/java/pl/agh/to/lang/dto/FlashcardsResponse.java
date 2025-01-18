package pl.agh.to.lang.dto;

import lombok.Value;
import pl.agh.to.lang.model.Flashcard;
import pl.agh.to.lang.model.Sentence;

import java.util.List;

@Value
public class FlashcardsResponse {
    Sentence sentence;

    List<Flashcard> flashcards;
}
