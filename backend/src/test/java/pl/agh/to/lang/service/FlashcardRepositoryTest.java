package pl.agh.to.lang.service;

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
        try {
            new Flashcard("   ");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
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

//    @Test
//    void testUpdateByWordTranslation() {
//        flashcardRepository.save(new Flashcard("hello"));
//        flashcardRepository.updateByWord("hello", "cześć");
//
//        Flashcard flashcard = flashcardRepository.findByWordOrThrow("hello");
//        assertEquals("cześć", flashcard.getTranslation());
//    }

//    @Test
//    void testUpdateByWordNonExistentFlashcard() {
//        assertThrows(NoSuchElementException.class, () -> flashcardRepository.updateByWord("nonexistent", "missing"));
//    }
//
//    @Test
//    void testRemoveFlashcard() {
//        flashcardRepository.add(new Flashcard("delete"));
//        flashcardRepository.removeByWord("delete");
//
//        List<Flashcard> flashcards = flashcardRepository.getAll();
//        assertTrue(flashcards.isEmpty());
//    }

//    @Test
//    void testRemoveNonExistentFlashcard() {
//        assertThrows(NoSuchElementException.class, () -> flashcardRepository.removeByWord("nonexistent"));
//    }
}

