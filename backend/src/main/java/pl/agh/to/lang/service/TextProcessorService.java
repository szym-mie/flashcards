package pl.agh.to.lang.service;

import org.springframework.stereotype.Service;
import pl.agh.to.lang.model.Direction;

import java.util.Arrays;
import java.util.List;

@Service
public class TextProcessorService {
    public List<String> extractWords(String text, Direction direction) {
        boolean shouldReverse = direction == Direction.LTR;
        String sanitizedText = text.replaceAll("[^\\p{L}\\p{Z}]", " ").toLowerCase();
        // if no words return empty list
        if (sanitizedText.isBlank()) return List.of();

        List<String> words = Arrays.asList(sanitizedText.split("\\s+"));
        List<String> orderedWords = shouldReverse ? words : words.reversed();
        return orderedWords.stream().distinct().toList();
    }
}
