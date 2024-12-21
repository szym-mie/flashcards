package pl.agh.to.lang.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LanguageTest {

    private final Validator validator;

    public LanguageTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = (Validator) factory.getValidator();
    }

    @Test
    void testValidLanguage() {
        Language language = new Language();
        language.setId("en");
        language.setName("English");

        Set<ConstraintViolation<Language>> violations = validator.validate(language);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testBlankIdThrowsValidationError() {
        Language language = new Language();
        language.setId("");
        language.setName("English");

        Set<ConstraintViolation<Language>> violations = validator.validate(language);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("id")));
    }

    @Test
    void testBlankNameThrowsValidationError() {
        Language language = new Language();
        language.setId("en");
        language.setName("");

        Set<ConstraintViolation<Language>> violations = validator.validate(language);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }
}
