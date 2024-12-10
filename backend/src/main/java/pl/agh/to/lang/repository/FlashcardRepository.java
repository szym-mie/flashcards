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

    public void add(Flashcard flashcard) {
        String key = flashcard.getWord();

        flashcards.putIfAbsent(key, flashcard);
    }

    public void updateByWord(String word, String translation) {
        Flashcard flashcard = findByWordOrThrow(word);
        flashcard.setTranslation(translation);
    }

    public void removeByWord(String word) {
        // ensure that flashcard exist
        Flashcard flashcard = findByWordOrThrow(word);
        flashcards.remove(flashcard.getWord());
    }
}
