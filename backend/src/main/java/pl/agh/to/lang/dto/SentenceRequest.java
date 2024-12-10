package pl.agh.to.lang.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SentenceRequest {
    @NotBlank
    private String text;
}
