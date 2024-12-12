package pl.agh.to.lang.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.to.lang.model.Language;
import pl.agh.to.lang.repository.LanguageRepository;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
@AllArgsConstructor
public class LanguageController {
    private final LanguageRepository languageRepository;

    @GetMapping
    public ResponseEntity<List<Language>> retrieveAllLanguages() {
        return ResponseEntity.ok(languageRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Language> createLanguage(@Valid @RequestBody Language language) {
        languageRepository.save(language);

        return ResponseEntity.noContent().build();
    }
}
