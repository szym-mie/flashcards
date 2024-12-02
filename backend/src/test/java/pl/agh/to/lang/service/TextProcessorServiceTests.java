package pl.agh.to.lang.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.agh.to.lang.dto.SentenceRequest;
import pl.agh.to.lang.util.Direction;

import java.util.Collections;
import java.util.List;

public class TextProcessorServiceTests {
    private final TextProcessorService service = new TextProcessorService();

    private void testString(String text, List<String> expected, Direction direction) {
        List<String> actual = service.extractWords(text, direction);
        Assertions.assertLinesMatch(expected, actual);
    }

    @Test
    public void testLTR() {
        Direction d = Direction.LTR;
        testString("", List.of(), d);
        testString("   \n\t", List.of(), d);
        testString("a", List.of("a"), d);
        testString("a b c", List.of("a", "b", "c"), d);
        testString("a\nb\tc", List.of("a", "b", "c"), d);
        testString("abc    def\n  ghi", List.of("abc", "def", "ghi"), d);
        testString("abc\n\t  bed\n\tcet", List.of("abc", "bed", "cet"), d);
        testString("123 !@# a b c", List.of("a", "b", "c"), d);
        testString("Привет мир", List.of("привет", "мир"), d);
    }

    @Test
    public void testRTL() {
        Direction d = Direction.RTL;
        testString("", List.of(), d);
        testString("   \n\t", List.of(), d);
        testString("a", List.of("a"), d);
        testString("a b c", List.of("c", "b", "a"), d);
        testString("a\nb\tc", List.of("c", "b", "a"), d);
        testString("abc    def\n  ghi", List.of("ghi", "def", "abc"), d);
        testString("abc\n\t  bed\n\tcet", List.of("cet", "bed", "abc"), d);
        testString("שלום עולם", List.of("עולם", "שלום"), d);
    }

    @Test
    public void testExtractWordsFromSentenceRequest() {
        List<String> result = service.extractWords("a b c", Direction.LTR);
        Assertions.assertEquals(List.of("a", "b", "c"), result);
    }

    @Test
    public void testLongText() {
        String longText = "a ".repeat(1000).trim();
        List<String> expected = Collections.nCopies(1000, "a");
        testString(longText, expected, Direction.LTR);
    }
}
