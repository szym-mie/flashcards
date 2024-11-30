package pl.agh.to.lang.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.agh.to.lang.exception.NotFoundException;
import pl.agh.to.lang.model.Flashcard;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class FlashcardServiceTest {

    private FlashcardService flashcardService;

    @BeforeEach
    public void setUp() {
        flashcardService = new FlashcardService();
    }

    @Test
    public void testGetAllInitiallyEmpty() {
        List<Flashcard> flashcards = flashcardService.getAll();
        assertTrue(flashcards.isEmpty());
    }

    @Test
    public void testAddFlashcard() {
        flashcardService.add("hello");
        List<Flashcard> flashcards = flashcardService.getAll();

        assertEquals(1, flashcards.size());
        assertEquals("hello", flashcards.get(0).getWord());
        assertEquals("", flashcards.get(0).getTranslation());
    }

    @Test
    public void testAddDuplicateFlashcard() {
        flashcardService.add("hello");
        flashcardService.add("hello");

        List<Flashcard> flashcards = flashcardService.getAll();
        assertEquals(1, flashcards.size());
    }

    @Test
    public void testAddBlankWord() {
        flashcardService.add("   ");
        List<Flashcard> flashcards = flashcardService.getAll();

        assertTrue(flashcards.isEmpty());
    }

    @Test
    public void testGetByWord() {
        flashcardService.add("world");
        Optional<Flashcard> flashcard = flashcardService.getByWord("world");

        assertTrue(flashcard.isPresent());
        assertEquals("world", flashcard.get().getWord());
    }

    @Test
    public void testGetByWordNonExistent() {
        Optional<Flashcard> flashcard = flashcardService.getByWord("nonexistent");
        assertTrue(flashcard.isEmpty());
    }

    @Test
    public void testGetByWordOrThrow() {
        flashcardService.add("example");
        Flashcard flashcard = flashcardService.getByWordOrThrow("example");

        assertEquals("example", flashcard.getWord());
    }

    @Test
    public void testGetByWordOrThrowNonExistent() {
        Exception exception = assertThrows(NotFoundException.class, () -> flashcardService.getByWordOrThrow("nonexistent"));

        assertEquals("Not found flashcard: nonexistent", exception.getMessage());
    }

    @Test
    public void testUpdateTranslation() {
        flashcardService.add("hello");
        flashcardService.update("hello", "cześć");

        Flashcard flashcard = flashcardService.getByWordOrThrow("hello");
        assertEquals("cześć", flashcard.getTranslation());
    }

    @Test
    public void testUpdateNonExistentFlashcard() {
        Exception exception = assertThrows(NotFoundException.class, () -> flashcardService.update("nonexistent", "missing"));

        assertEquals("Not found flashcard: nonexistent", exception.getMessage());
    }

    @Test
    public void testRemoveFlashcard() {
        flashcardService.add("delete");
        flashcardService.remove("delete");

        List<Flashcard> flashcards = flashcardService.getAll();
        assertTrue(flashcards.isEmpty());
    }

    @Test
    public void testRemoveNonExistentFlashcard() {
        Exception exception = assertThrows(NotFoundException.class, () -> flashcardService.remove("nonexistent"));

        assertEquals("Not found flashcard: nonexistent", exception.getMessage());
    }
}

