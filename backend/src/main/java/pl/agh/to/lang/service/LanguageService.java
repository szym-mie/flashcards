package pl.agh.to.lang.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.agh.to.lang.exception.ResourceAlreadyExistsException;
import pl.agh.to.lang.model.Language;
import pl.agh.to.lang.repository.LanguageRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class LanguageService {
    private final LanguageRepository languageRepository;

    public List<Language> getAll() {
        return languageRepository.findAll();
    }

    public Language create(Language language) {
        language.setId(language.getId().toLowerCase());

        if (languageRepository.existsById(language.getId())) {
            throw new ResourceAlreadyExistsException("Language already exists (use PUT to update)");
        }
        languageRepository.save(language);

        return language;
    }

    public void update(Language language) {
        language.setId(language.getId().toLowerCase());

        if (!languageRepository.existsById(language.getId())) {
            throw new NoSuchElementException("Language does not exist (use POST to create)");
        }
        languageRepository.save(language);
    }
}
