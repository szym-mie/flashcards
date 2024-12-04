package pl.agh.to.lang.service;

import org.springframework.stereotype.Service;
import pl.agh.to.lang.dto.SentenceRequest;
import pl.agh.to.lang.util.Direction;

import java.util.Arrays;
import java.util.List;

@Service
public class TextProcessorService {
    public List<String> extractWords(String text, Direction direction) {
        boolean shouldReverse = direction == Direction.RTL;
        String sanitizedText = text.replaceAll("[^\\p{L}\\p{Z}]", " ")
                .toLowerCase();

        List<String> words = Arrays.stream(sanitizedText.split("\\s+"))
                .filter(word -> !word.isBlank())
                .toList();

        return shouldReverse ? words.reversed() : words;
    }

    public List<String> extractWords(SentenceRequest sentenceRequest) {
        return this.extractWords(sentenceRequest.getText(), sentenceRequest.getDirection());
    }
}
