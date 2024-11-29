package pl.agh.to.lang.service;

import org.springframework.stereotype.Service;
import pl.agh.to.lang.model.Direction;

import java.util.Arrays;
import java.util.List;

@Service
public class TextProcessorService {
    public List<String> extractWords(String text, Direction direction) {

        if (direction == Direction.RTL) {
            text = new StringBuilder(text).reverse().toString();
        }

        String sanitizedText = text.replaceAll("[^\\p{L}\\p{Z}]", " ").toLowerCase();
        // if no words return empty list
        if (sanitizedText.isBlank()) return List.of();

        List<String> words = Arrays.asList(sanitizedText.split("\\s+"));

        if (direction == Direction.RTL) {
            return words.stream()
                    .distinct()
                    .map(str -> new StringBuilder(str).reverse().toString())
                    .toList();
        }
        return words.stream().distinct().toList();
    }
}
