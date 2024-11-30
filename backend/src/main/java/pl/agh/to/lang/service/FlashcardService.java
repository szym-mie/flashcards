package pl.agh.to.lang.service;

import org.springframework.stereotype.Service;
import pl.agh.to.lang.exception.NotFoundException;
import pl.agh.to.lang.model.Flashcard;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FlashcardService {
    private final List<Flashcard> flashcards = new LinkedList<>();

    public List<Flashcard> getAll() {
        return flashcards;
    }

    public Optional<Flashcard> getByWord(String word) {
        return flashcards.stream().filter(flashcard -> Objects.equals(flashcard.getWord(), word)).findAny();
    }

    public Flashcard getByWordOrThrow(String word) {
        Optional<Flashcard> flashcard = getByWord(word);

        if (flashcard.isEmpty()) {
            throw new NotFoundException("Not found flashcard: " + word);
        }

        return flashcard.get();
    }

    public void add(String word) {
        Optional<Flashcard> flashcard = getByWord(word);

        flashcard.ifPresent(value -> remove(value.getWord()));

        flashcards.add(new Flashcard(word, ""));
    }

    public void update(String word, String translation) {
        Flashcard flashcard = getByWordOrThrow(word);
        flashcard.setTranslation(translation);
    }

    public void remove(String word) {
        Flashcard flashcard = getByWordOrThrow(word);
        flashcards.remove(flashcard);
    }
}
