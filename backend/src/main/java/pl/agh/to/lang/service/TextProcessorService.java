package pl.agh.to.lang.service;

import org.springframework.stereotype.Service;
import pl.agh.to.lang.dto.SentenceRequest;
import pl.agh.to.lang.util.Direction;

import java.util.Arrays;
import java.util.List;

@Service
public class TextProcessorService {
    public List<String> extractWords(String text, Direction direction) {
//        String sanitizedText = text.replaceAll("[^\\p{L}\\p{Z}]", " ")
//                .toLowerCase();

        return Arrays.stream(text.split("[ \\n\\t\\p{Z}]+"))
                .filter(word -> !word.isBlank())
                .toList();
    }

    public List<String> extractWords(SentenceRequest sentenceRequest) {
        return this.extractWords(sentenceRequest.getText(), sentenceRequest.getDirection());
    }
}
