package pl.agh.to.lang.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TranslationRequest {
    @NotBlank
    private String text;
}
