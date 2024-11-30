package pl.agh.to.lang.service;

import org.springframework.stereotype.Service;
import pl.agh.to.lang.model.Direction;

import java.util.Arrays;
import java.util.List;

@Service
public class TextProcessorService {
    public List<String> extractWords(String text, Direction direction) {
        boolean shouldReverse = direction == Direction.RTL;
        String sanitizedText = text.replaceAll("[^\\p{L}\\p{Z}]", " ").toLowerCase();

        List<String> words = Arrays.asList(sanitizedText.split("\\s+"));

        return shouldReverse ? words.reversed() : words;
    }
}
