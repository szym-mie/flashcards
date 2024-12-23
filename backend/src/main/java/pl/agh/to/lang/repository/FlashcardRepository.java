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

    public List<Flashcard> findAll() {
        return flashcards.values().stream().toList();
    }

    public Optional<Flashcard> findByWord(String word) {
        return Optional.ofNullable(flashcards.get(word));
    }

    public void save(Flashcard flashcard) {
        String key = flashcard.getWord();

        flashcards.putIfAbsent(key, flashcard);
    }

    public void update(Flashcard flashcard) {
        Optional<Flashcard> optionalFlashcard = findByWord(flashcard.getWord());

        optionalFlashcard.ifPresent(foundFlashcard -> {
            foundFlashcard.setLemma(flashcard.getLemma());
            foundFlashcard.setTranslation(flashcard.getTranslation());
            foundFlashcard.setPartOfSpeech(flashcard.getPartOfSpeech());
            foundFlashcard.setTranscription(flashcard.getTranscription());
        });
    }

    public void delete(Flashcard flashcard) {
        flashcards.remove(flashcard.getWord());
    }
}
