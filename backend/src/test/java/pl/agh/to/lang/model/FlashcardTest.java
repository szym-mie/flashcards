package pl.agh.to.lang.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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

    @Test
    void testSettersAndGetters() {
        Flashcard flashcard = new Flashcard("hello");
        flashcard.setLemma("hello");
        flashcard.setTranslation("cześć");
        flashcard.setPartOfSpeech("noun");
        flashcard.setTranscription("həˈloʊ");

        assertEquals("hello", flashcard.getLemma());
        assertEquals("cześć", flashcard.getTranslation());
        assertEquals("noun", flashcard.getPartOfSpeech());
        assertEquals("həˈloʊ", flashcard.getTranscription());
    }
}
