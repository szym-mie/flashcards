package pl.agh.to.lang.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.agh.to.lang.util.Direction;

import java.util.Collections;
import java.util.List;

class TextProcessorServiceTests {
    private final TextProcessorService service = new TextProcessorService();

    private void testString(String text, List<String> expected, Direction direction) {
        List<String> actual = service.extractWords(text, direction);
        Assertions.assertLinesMatch(expected, actual);
    }

    @Test
    void testLTR() {
        Direction d = Direction.LTR;
        testString("", List.of(), d);
        testString("   \n\t", List.of(), d);
        testString("a", List.of("a"), d);
        testString("a b c", List.of("a", "b", "c"), d);
        testString("a\nb\tc", List.of("a", "b", "c"), d);
        testString("abc    def\n  ghi", List.of("abc", "def", "ghi"), d);
        testString("abc\n\t  bed\n\tcet", List.of("abc", "bed", "cet"), d);
        testString("123 !@# a b c", List.of("123", "!@#", "a", "b", "c"), d);
        testString("Привет мир", List.of("Привет", "мир"), d);
    }

    @Test
    void testRTL() {
        Direction d = Direction.RTL;
        testString("", List.of(), d);
        testString("   \n\t", List.of(), d);
        testString("a", List.of("a"), d);
        testString("a b c", List.of("a", "b", "c"), d);
        testString("a\nb\tc", List.of("a", "b", "c"), d);
        testString("abc    def\n  ghi", List.of("abc", "def", "ghi"), d);
        testString("abc\n\t  bed\n\tcet", List.of("abc", "bed", "cet"), d);
        testString("שלום עולם", List.of("שלום", "עולם"), d);
        testString("אַמְרָפֶ֣ל  מֶֽלֶךְ־ שִׁנְעָ֔ר", List.of("אַמְרָפֶ֣ל", "מֶֽלֶךְ־", "שִׁנְעָ֔ר"), d);
    }

    @Test
    void testExtractWordsFromSentenceRequest() {
        List<String> result = service.extractWords("a b c", Direction.LTR);
        Assertions.assertEquals(List.of("a", "b", "c"), result);
    }

    @Test
    void testLongText() {
        String longText = "a ".repeat(1000).trim();
        List<String> expected = Collections.nCopies(1000, "a");
        testString(longText, expected, Direction.LTR);
    }
}
