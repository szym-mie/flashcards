package pl.agh.to.lang.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.agh.to.lang.exception.NotFoundException;
import pl.agh.to.lang.model.Flashcard;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FlashcardServiceTest {

    private FlashcardService flashcardService;

    @BeforeEach
    void setUp() {
        flashcardService = new FlashcardService();
    }

    @Test
    void testGetAllInitiallyEmpty() {
        List<Flashcard> flashcards = flashcardService.getAll();
        assertTrue(flashcards.isEmpty());
    }

    @Test
    void testAddFlashcard() {
        flashcardService.add("hello");
        List<Flashcard> flashcards = flashcardService.getAll();

        assertEquals(1, flashcards.size());
        assertEquals("hello", flashcards.get(0).getWord());
        assertEquals("", flashcards.get(0).getTranslation());
    }

    @Test
    void testAddDuplicateFlashcard() {
        flashcardService.add("hello");
        flashcardService.add("hello");

        List<Flashcard> flashcards = flashcardService.getAll();
        assertEquals(1, flashcards.size());
    }

    @Test
    void testAddBlankWord() {
        flashcardService.add("   ");
        List<Flashcard> flashcards = flashcardService.getAll();

        assertTrue(flashcards.isEmpty());
    }

    @Test
    void testGetByWord() {
        flashcardService.add("world");
        Optional<Flashcard> flashcard = flashcardService.getByWord("world");

        assertTrue(flashcard.isPresent());
        assertEquals("world", flashcard.get().getWord());
    }

    @Test
    void testGetByWordNonExistent() {
        Optional<Flashcard> flashcard = flashcardService.getByWord("nonexistent");
        assertTrue(flashcard.isEmpty());
    }

    @Test
    void testGetByWordOrThrow() {
        flashcardService.add("example");
        Flashcard flashcard = flashcardService.getByWordOrThrow("example");

        assertEquals("example", flashcard.getWord());
    }

    @Test
    void testGetByWordOrThrowNonExistent() {
        Exception exception = assertThrows(NotFoundException.class, () -> flashcardService.getByWordOrThrow("nonexistent"));

        assertEquals("Not found flashcard: nonexistent", exception.getMessage());
    }

    @Test
    void testUpdateTranslation() {
        flashcardService.add("hello");
        flashcardService.update("hello", "cześć");

        Flashcard flashcard = flashcardService.getByWordOrThrow("hello");
        assertEquals("cześć", flashcard.getTranslation());
    }

    @Test
    void testUpdateNonExistentFlashcard() {
        Exception exception = assertThrows(NotFoundException.class, () -> flashcardService.update("nonexistent", "missing"));

        assertEquals("Not found flashcard: nonexistent", exception.getMessage());
    }

    @Test
    void testRemoveFlashcard() {
        flashcardService.add("delete");
        flashcardService.remove("delete");

        List<Flashcard> flashcards = flashcardService.getAll();
        assertTrue(flashcards.isEmpty());
    }

    @Test
    void testRemoveNonExistentFlashcard() {
        Exception exception = assertThrows(NotFoundException.class, () -> flashcardService.remove("nonexistent"));

        assertEquals("Not found flashcard: nonexistent", exception.getMessage());
    }
}

