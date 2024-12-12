package pl.agh.to.lang.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.to.lang.model.Language;
import pl.agh.to.lang.model.Lemma;
import pl.agh.to.lang.repository.LemmaRepository;

@RestController
@RequestMapping("/api/lemmas")
@AllArgsConstructor
public class LemmaController {
    private final LemmaRepository lemmaRepository;

    @GetMapping("/{name}")
    public ResponseEntity<Lemma> retrieveLemmaByNameAndLanguage(@PathVariable(value = "name") String name, @RequestParam Language language) {
        Lemma lemma = lemmaRepository.findOneByNameAndLanguage(name, language).orElseThrow();

        return ResponseEntity.ok(lemma);
    }

    @PostMapping
    public ResponseEntity<Lemma> upsertLemma(@Valid @RequestBody Lemma lemma) {
        lemmaRepository.save(lemma);

        return ResponseEntity.noContent().build();
    }
}
