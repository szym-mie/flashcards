package pl.agh.to.lang.dto;

import lombok.Value;
import pl.agh.to.lang.helpers.PhraseType;

@Value
public class Phrase {
    private String text;

    private PhraseType type;
}
