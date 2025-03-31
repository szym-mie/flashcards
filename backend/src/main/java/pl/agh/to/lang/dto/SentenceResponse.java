package pl.agh.to.lang.dto;

import lombok.Value;
import pl.agh.to.lang.model.Language;

import java.util.List;

@Value
public class SentenceResponse {
    private String text;

    private Language language;

    private List<Phrase> phrases;

}
