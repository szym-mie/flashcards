package pl.agh.to.lang.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


class FlashcardTest {

    @Test
    void testConstructorWithValidWord() {
        Flashcard flashcard = new Flashcard("hello");

        assertEquals("hello", flashcard.getWord());
        assertEquals("", flashcard.getTranslation());
        assertNull(flashcard.getPartOfSpeech());
        assertNull(flashcard.getPartOfSentence());
        assertEquals("", flashcard.getTranscription());
    }

    @Test
    void testConstructorWithBlankWordThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Flashcard(" "));
        assertEquals("Blank word", exception.getMessage());
    }
}
