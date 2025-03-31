package pl.agh.to.lang.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class Sentence {
    @NotBlank
    private String text;

    @NotNull
    private Language language;
}