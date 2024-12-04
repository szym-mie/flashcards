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

public class FlashcardRepositoryTest {

    private FlashcardRepository flashcardRepository;

    @BeforeEach
    public void setUp() {
        flashcardRepository = new FlashcardRepository();
    }

    @Test
    public void testGetAllInitiallyEmpty() {
        List<Flashcard> flashcards = flashcardRepository.getAll();
        assertTrue(flashcards.isEmpty());
    }

    @Test
    public void testAddFlashcard() {
        flashcardRepository.add(new Flashcard("hello"));
        List<Flashcard> flashcards = flashcardRepository.getAll();

        assertEquals(1, flashcards.size());
        assertEquals("hello", flashcards.getFirst().getWord());
        assertEquals("", flashcards.getFirst().getTranslation());
    }

    @Test
    public void testAddDuplicateFlashcard() {
        flashcardRepository.add(new Flashcard("hello"));
        flashcardRepository.add(new Flashcard("hello"));

        List<Flashcard> flashcards = flashcardRepository.getAll();
        assertEquals(1, flashcards.size());
    }

    @Test
    public void testAddBlankWord() {
        assertThrows(IllegalArgumentException.class, () -> flashcardRepository.add(new Flashcard("   ")));
    }

    @Test
    public void testFindByWord() {
        flashcardRepository.add(new Flashcard("world"));
        Optional<Flashcard> flashcard = flashcardRepository.findByWord("world");

        assertTrue(flashcard.isPresent());
        assertEquals("world", flashcard.get().getWord());
    }

    @Test
    public void testFindByWordNonExistent() {
        Optional<Flashcard> flashcard = flashcardRepository.findByWord("nonexistent");
        assertTrue(flashcard.isEmpty());
    }

    @Test
    public void testFindByWordOrThrow() {
        flashcardRepository.add(new Flashcard("example"));
        Flashcard flashcard = flashcardRepository.findByWordOrThrow("example");

        assertEquals("example", flashcard.getWord());
    }

    @Test
    public void testFindByWordOrThrowNonExistent() {
        assertThrows(NoSuchElementException.class, () -> flashcardRepository.findByWordOrThrow("nonexistent"));
    }

    @Test
    public void testUpdateByWordTranslation() {
        flashcardRepository.add(new Flashcard("hello"));
        flashcardRepository.updateByWord("hello", "cześć");

        Flashcard flashcard = flashcardRepository.findByWordOrThrow("hello");
        assertEquals("cześć", flashcard.getTranslation());
    }

    @Test
    public void testUpdateByWordNonExistentFlashcard() {
        assertThrows(NoSuchElementException.class, () -> flashcardRepository.updateByWord("nonexistent", "missing"));
    }

    @Test
    public void testRemoveFlashcard() {
        flashcardRepository.add(new Flashcard("delete"));
        flashcardRepository.removeByWord("delete");

        List<Flashcard> flashcards = flashcardRepository.getAll();
        assertTrue(flashcards.isEmpty());
    }

    @Test
    public void testRemoveNonExistentFlashcard() {
        assertDoesNotThrow(() -> flashcardRepository.removeByWord("nonexistent"));
    }
}

