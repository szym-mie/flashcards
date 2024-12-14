package pl.agh.to.lang.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.agh.to.lang.model.Flashcard;
import pl.agh.to.lang.repository.FlashcardRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class FlashcardRepositoryTest {

    private FlashcardRepository flashcardRepository;

    @BeforeEach
    void setUp() {
        flashcardRepository = new FlashcardRepository();
    }

    @Test
    void testGetAllInitiallyEmpty() {
        List<Flashcard> flashcards = flashcardRepository.getAll();
        assertTrue(flashcards.isEmpty());
    }

    @Test
    void testAddFlashcard() {
        flashcardRepository.save(new Flashcard("hello"));
        List<Flashcard> flashcards = flashcardRepository.getAll();

        assertEquals(1, flashcards.size());
        assertEquals("hello", flashcards.getFirst().getWord());
        assertEquals("", flashcards.getFirst().getTranslation());
    }

    @Test
    void testAddDuplicateFlashcard() {
        flashcardRepository.save(new Flashcard("hello"));
        flashcardRepository.save(new Flashcard("hello"));

        List<Flashcard> flashcards = flashcardRepository.getAll();
        assertEquals(1, flashcards.size());
    }

    @Test
    void testAddBlankWord() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Flashcard("   "));
        assertEquals("Blank word", exception.getMessage());
    }

    @Test
    void testFindByWord() {
        flashcardRepository.save(new Flashcard("world"));
        Optional<Flashcard> flashcard = flashcardRepository.findByWord("world");

        assertTrue(flashcard.isPresent());
        assertEquals("world", flashcard.get().getWord());
    }

    @Test
    void testFindByWordNonExistent() {
        Optional<Flashcard> flashcard = flashcardRepository.findByWord("nonexistent");
        assertTrue(flashcard.isEmpty());
    }

    @Test
    void testFindByWordOrThrow() {
        flashcardRepository.save(new Flashcard("example"));
        Flashcard flashcard = flashcardRepository.findByWordOrThrow("example");

        assertEquals("example", flashcard.getWord());
    }

    @Test
    void testFindByWordOrThrowNonExistent() {
        assertThrows(NoSuchElementException.class, () -> flashcardRepository.findByWordOrThrow("nonexistent"));
    }

    @Test
    void testUpdateFlashcard() {
        Flashcard flashcard = new Flashcard("hello");
        flashcard.setTranslation("cześć");
        flashcard.setLemma("hello");
        flashcard.setPartOfSpeech("noun");
        flashcard.setTranscription("he-lo");

        flashcardRepository.save(flashcard);

        Flashcard updatedFlashcard = new Flashcard("hello");
        updatedFlashcard.setTranslation("hi");
        updatedFlashcard.setLemma("greet");
        updatedFlashcard.setPartOfSpeech("verb");
        updatedFlashcard.setTranscription("hɪ");

        flashcardRepository.update(updatedFlashcard);

        Flashcard result = flashcardRepository.findByWordOrThrow("hello");

        assertEquals("hi", result.getTranslation());
        assertEquals("greet", result.getLemma());
        assertEquals("verb", result.getPartOfSpeech());
        assertEquals("hɪ", result.getTranscription());
    }

    @Test
    void testUpdateNonExistentFlashcard() {
        Flashcard flashcard = new Flashcard("nonexistent");
        assertThrows(NoSuchElementException.class, () -> flashcardRepository.update(flashcard));
    }

    @Test
    void testRemoveFlashcard() {
        Flashcard flashcard = new Flashcard("delete");
        flashcardRepository.save(flashcard);

        flashcardRepository.remove(flashcard);

        List<Flashcard> flashcards = flashcardRepository.getAll();
        assertTrue(flashcards.isEmpty());
    }

    @Test
    void testRemoveNonExistentFlashcard() {
        Flashcard flashcard = new Flashcard("nonexistent");
        assertThrows(NoSuchElementException.class, () -> flashcardRepository.remove(flashcard));
    }

}

