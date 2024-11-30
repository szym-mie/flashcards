package pl.agh.to.lang.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SentenceRequest {
    @NotBlank
    private String text;

    private Direction direction;
}
