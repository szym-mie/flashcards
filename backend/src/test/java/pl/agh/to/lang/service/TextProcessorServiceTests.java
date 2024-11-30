package pl.agh.to.lang.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.agh.to.lang.util.Direction;

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
    }
}
