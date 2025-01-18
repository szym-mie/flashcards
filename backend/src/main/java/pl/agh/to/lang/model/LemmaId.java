package pl.agh.to.lang.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/* This class is needed for JPA implementation of Composite Key for Lemma Entity */

@Data
@NoArgsConstructor // for JPA
public class LemmaId implements Serializable {
    private String name;

    private String language;
}
