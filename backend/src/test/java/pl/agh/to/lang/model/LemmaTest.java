package pl.agh.to.lang.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class LemmaTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testBlankNameThrowsValidationError() {
        Lemma lemma = new Lemma();
        lemma.setName("");
        lemma.setTranslation("translation");

        Set<ConstraintViolation<Lemma>> violations = validator.validate(lemma);

        assertFalse(violations.isEmpty(), "Violations should not be empty");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")),
                "Violation for 'name' should be present");
    }

    @Test
    void testNullTranslationThrowsValidationError() {
        Lemma lemma = new Lemma();
        lemma.setName("validName");
        lemma.setTranslation(null);

        Set<ConstraintViolation<Lemma>> violations = validator.validate(lemma);

        assertFalse(violations.isEmpty(), "Violations should not be empty");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("translation")),
                "Violation for 'translation' should be present");
    }
}
