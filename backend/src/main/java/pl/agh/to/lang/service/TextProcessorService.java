package pl.agh.to.lang.service;

import org.springframework.stereotype.Service;
import pl.agh.to.lang.dto.SentenceRequest;
import pl.agh.to.lang.util.Direction;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TextProcessorService {
    public List<String> extractWords(String text, Direction direction) {
        Matcher matcher = wordPattern.matcher(text);
        return matcher.results().map(MatchResult::group).toList();
    }

    public List<String> extractWords(SentenceRequest sentenceRequest) {
        return this.extractWords(sentenceRequest.getText(), sentenceRequest.getDirection());
    }

    private static final Pattern wordPattern = Pattern.compile("[^\\p{Pe}\\p{Po}\\p{Z}\\p{C}]+");
}
