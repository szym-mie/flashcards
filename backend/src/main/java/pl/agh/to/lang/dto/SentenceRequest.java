package pl.agh.to.lang.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import pl.agh.to.lang.util.Direction;

@Data
public class SentenceRequest {
    @NotBlank
    private String text;

    private Direction direction;
}
