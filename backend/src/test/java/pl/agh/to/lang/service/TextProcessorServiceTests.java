package pl.agh.to.lang.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class TextProcessorServiceTests {
    private final TextProcessorService service = new TextProcessorService();

    private void testString(String text, List<String> expected) {
        List<String> actual = service.extractWords(text);
        Assertions.assertLinesMatch(expected, actual);
    }

    @Test
    void testLTR() {
        testString("", List.of());
        testString("   \n\t", List.of());
        testString("a", List.of("a"));
        testString("a b c", List.of("a", "b", "c"));
        testString("a\nb\tc", List.of("a", "b", "c"));
        testString("abc    def\n  ghi", List.of("abc", "def", "ghi"));
        testString("abc\n\t  bed\n\tcet", List.of("abc", "bed", "cet"));
        testString("123 !@# a b c", List.of("123", "a", "b", "c"));
        testString("Привет мир", List.of("Привет", "мир"));
        testString("¡Oiga! ¿Sabe dónde está la parada?",
                List.of("Oiga", "Sabe", "dónde", "está", "la", "parada"));
        testString("\"Оценки результатов перестройки в обществе разнятся.\"",
                List.of("Оценки", "результатов", "перестройки", "в", "обществе", "разнятся"));
    }

    @Test
    void testRTL() {
        testString("", List.of());
        testString("   \n\t", List.of());
        testString("a", List.of("a"));
        testString("a b c", List.of("a", "b", "c"));
        testString("a\nb\tc", List.of("a", "b", "c"));
        testString("abc    def\n  ghi", List.of("abc", "def", "ghi"));
        testString("abc\n\t  bed\n\tcet", List.of("abc", "bed", "cet"));
        testString("שלום עולם", List.of("שלום", "עולם"));
        testString("אַמְרָפֶ֣ל  מֶֽלֶךְ־ שִׁנְעָ֔ר", List.of("אַמְרָפֶ֣ל", "מֶֽלֶךְ־", "שִׁנְעָ֔ר"));
    }

    @Test
    void testExtractWordsFromSentenceRequest() {
        List<String> result = service.extractWords("a b c");
        Assertions.assertEquals(List.of("a", "b", "c"), result);
    }

    @Test
    void testLongText() {
        String longText = "a ".repeat(1000).trim();
        List<String> expected = Collections.nCopies(1000, "a");
        testString(longText, expected);
    }
}
