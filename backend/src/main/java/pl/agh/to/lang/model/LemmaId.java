package pl.agh.to.lang.model;

import lombok.Data;

import java.io.Serializable;

/* This class is needed for JPA implementation of Composite Key for Lemma Entity */

@Data
public class LemmaId implements Serializable {
    private String name;

    private String language;

    public LemmaId() { /* It is needed for JPA */ }
}
