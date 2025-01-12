package pl.agh.to.lang.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.agh.to.lang.model.Flashcard;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FlashcardRepositoryTest {

    private FlashcardRepository flashcardRepository;

    @BeforeEach
    void setUp() {
        flashcardRepository = new FlashcardRepository();
    }

    @Test
    void testFindAllInitiallyEmpty() {
        List<Flashcard> flashcards = flashcardRepository.findAll();
        assertTrue(flashcards.isEmpty());
    }

    @Test
    void testSaveFlashcard() {
        flashcardRepository.save(new Flashcard("hello"));
        List<Flashcard> flashcards = flashcardRepository.findAll();

        assertEquals(1, flashcards.size());
        assertEquals("hello", flashcards.getFirst().getWord());
        assertEquals("", flashcards.getFirst().getTranslation());
    }

    @Test
    void testSaveDuplicateFlashcard() {
        flashcardRepository.save(new Flashcard("hello"));
        flashcardRepository.save(new Flashcard("hello"));

        List<Flashcard> flashcards = flashcardRepository.findAll();
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

        Optional<Flashcard> optionalResult = flashcardRepository.findByWord("hello");

        optionalResult.ifPresent(result -> {
            assertEquals("hi", result.getTranslation());
            assertEquals("greet", result.getLemma());
            assertEquals("verb", result.getPartOfSpeech());
            assertEquals("hɪ", result.getTranscription());
        });
    }

    @Test
    void testDeleteFlashcard() {
        Flashcard flashcard = new Flashcard("delete");
        flashcardRepository.save(flashcard);

        flashcardRepository.delete(flashcard);

        List<Flashcard> flashcards = flashcardRepository.findAll();
        assertTrue(flashcards.isEmpty());
    }
}

