package pl.agh.to.lang.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import pl.agh.to.lang.util.Direction;

@Getter
public class SentenceRequest {
    @NotBlank
    private String text;

    private Direction direction;
}
