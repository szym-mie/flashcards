package pl.agh.to.lang.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlashcardTest {

    @Test
    void testConstructorWithValidWord() {
        Flashcard flashcard = new Flashcard("hello");

        assertEquals("hello", flashcard.getWord());
        assertEquals("", flashcard.getTranslation());
        assertEquals("", flashcard.getPartOfSpeech());
        assertEquals("", flashcard.getTranscription());
    }

    @Test
    void testConstructorWithBlankWordThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Flashcard(" "));
        assertEquals("Blank word", exception.getMessage());
    }
}
