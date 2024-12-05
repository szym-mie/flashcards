package pl.agh.to.lang.repository;

import org.springframework.stereotype.Repository;
import pl.agh.to.lang.model.Flashcard;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class FlashcardRepository {
    private final Map<String, Flashcard> flashcards = new HashMap<>();

    public List<Flashcard> getAll() {
        List<Flashcard> flashcardList = new LinkedList<>(flashcards.values());
        // changes made in list would be reflected in the map
        return Collections.unmodifiableList(flashcardList);
    }

    public Optional<Flashcard> findByWord(String word) {
        return Optional.ofNullable(flashcards.get(word));
    }

    public Flashcard findByWordOrThrow(String word) {
        return findByWord(word).orElseThrow();
    }

    public boolean add(Flashcard flashcard) {
        // we don't want to override previous translation
        String key = flashcard.getWord();

        if (flashcards.get(key) == null) {
            flashcards.put(key, flashcard);
            return true;
        }

        return false;
    }

    public List<Flashcard> addAll(Collection<Flashcard> flashcardColl) {
        return flashcardColl.stream()
                .filter(this::add)
                .toList();
    }

    public void updateByWord(String word, String translation) {
        Flashcard flashcard = findByWordOrThrow(word);
        flashcard.setTranslation(translation);
    }

    public void removeByWord(String word) {
        flashcards.remove(word);
    }
}
