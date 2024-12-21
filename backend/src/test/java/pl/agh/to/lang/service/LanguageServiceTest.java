package pl.agh.to.lang.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.agh.to.lang.model.Language;
import pl.agh.to.lang.repository.LanguageRepository;
import pl.agh.to.lang.exception.ResourceAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LanguageServiceTest {

    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private LanguageService languageService;

    @Test
    void testGetAllLanguages() {
        Language language1 = new Language();
        language1.setId("en");
        language1.setName("English");

        Language language2 = new Language();
        language2.setId("pl");
        language2.setName("Polish");

        when(languageRepository.findAll()).thenReturn(List.of(language1, language2));

        List<Language> languages = languageService.getAll();

        assertEquals(2, languages.size());
        assertEquals("en", languages.get(0).getId());
        assertEquals("English", languages.get(0).getName());
        assertEquals("pl", languages.get(1).getId());
        assertEquals("Polish", languages.get(1).getName());
        verify(languageRepository, times(1)).findAll();
    }

    @Test
    void testCreateLanguageSuccess() {
        Language language = new Language();
        language.setId("EN");
        language.setName("English");

        when(languageRepository.existsById("en")).thenReturn(false);
        when(languageRepository.save(any(Language.class))).thenReturn(language);

        Language createdLanguage = languageService.create(language);

        assertEquals("en", createdLanguage.getId());
        assertEquals("English", createdLanguage.getName());
        verify(languageRepository, times(1)).existsById("en");
        verify(languageRepository, times(1)).save(any(Language.class));
    }

    @Test
    void testCreateLanguageAlreadyExists() {
        Language language = new Language();
        language.setId("EN");
        language.setName("English");

        when(languageRepository.existsById("en")).thenReturn(true);

        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
            languageService.create(language);
        });

        assertEquals("Language already exists (use PUT to update)", exception.getMessage());
        verify(languageRepository, times(1)).existsById("en");
        verify(languageRepository, never()).save(any(Language.class));
    }

    @Test
    void testUpdateLanguageSuccess() {
        Language language = new Language();
        language.setId("EN");
        language.setName("English");

        when(languageRepository.existsById("en")).thenReturn(true);
        when(languageRepository.save(any(Language.class))).thenReturn(language);

        assertDoesNotThrow(() -> languageService.update(language));

        verify(languageRepository, times(1)).existsById("en");
        verify(languageRepository, times(1)).save(any(Language.class));
    }

    @Test
    void testUpdateLanguageDoesNotExist() {
        Language language = new Language();
        language.setId("EN");
        language.setName("English");

        when(languageRepository.existsById("en")).thenReturn(false);

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            languageService.update(language);
        });

        assertEquals("Language does not exist (use POST to create)", exception.getMessage());
        verify(languageRepository, times(1)).existsById("en");
        verify(languageRepository, never()).save(any(Language.class));
    }
}
