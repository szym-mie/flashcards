package pl.agh.to.lang.repository;

import org.springframework.stereotype.Repository;
import pl.agh.to.lang.model.Flashcard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class FlashcardRepository {
    private final Map<String, Flashcard> flashcards = new HashMap<>();

    public List<Flashcard> getAll() {
        return flashcards.values().stream().toList();
    }

    public Optional<Flashcard> findByWord(String word) {
        return Optional.ofNullable(flashcards.get(word));
    }

    public Flashcard findByWordOrThrow(String word) {
        return findByWord(word).orElseThrow();
    }

    public void save(Flashcard flashcard) {
        String key = flashcard.getWord();

        flashcards.putIfAbsent(key, flashcard);
    }

    public void update(Flashcard flashcard) {
        Flashcard foundFlashcard = findByWordOrThrow(flashcard.getWord());

        foundFlashcard.setLemma(flashcard.getLemma());
        foundFlashcard.setPartOfSpeech(flashcard.getPartOfSpeech());
        foundFlashcard.setTranscription(flashcard.getTranscription());
    }

    public void remove(Flashcard flashcard) {
        // ensure that flashcard exist
        String word = flashcard.getWord();
        findByWordOrThrow(word);

        flashcards.remove(word);
    }
}
