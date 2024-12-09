package pl.agh.to.lang.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TextProcessorService {
    private static final Pattern wordPattern = Pattern.compile("[^\\p{Pe}\\p{Po}\\p{Z}\\p{C}]+");

    public List<String> extractWords(String text) {
        Matcher matcher = wordPattern.matcher(text);
        return matcher.results().map(MatchResult::group).toList();
    }
}
