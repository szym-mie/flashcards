package pl.agh.to.lang.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Language {
    @Id
    @NotBlank
    private String id;

    @NotBlank
    private String name;
}
