package pl.agh.to.lang.model;

import jakarta.validation.*;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;


class SentenceTest {

    private final Validator validator;

    public SentenceTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testValidSentence() {
        Language language = new Language();
        language.setId("en");
        language.setName("English");

        Sentence sentence = new Sentence("This is a valid sentence.", language);

        Set<ConstraintViolation<Sentence>> violations = validator.validate(sentence);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testBlankTextThrowsValidationError() {
        Language language = new Language();
        language.setId("en");
        language.setName("English");

        Sentence sentence = new Sentence("", language);

        Set<ConstraintViolation<Sentence>> violations = validator.validate(sentence);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("text")));
    }

    @Test
    void testNullLanguageThrowsValidationError() {
        Sentence sentence = new Sentence("This is a valid sentence.", null);

        Set<ConstraintViolation<Sentence>> violations = validator.validate(sentence);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("language")));
    }
}


