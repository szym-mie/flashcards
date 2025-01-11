package pl.agh.to.lang.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.to.lang.dto.FlashcardsResponse;
import pl.agh.to.lang.export.FlashcardCsvExporter;
import pl.agh.to.lang.model.Flashcard;
import pl.agh.to.lang.model.Sentence;
import pl.agh.to.lang.service.FlashcardService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
@RequiredArgsConstructor
public class FlashcardController {
    private final FlashcardService flashcardService;
    private final FlashcardCsvExporter csvExporter = new FlashcardCsvExporter();

    @GetMapping
    public ResponseEntity<List<Flashcard>> retrieveAllFlashcards() {
        return ResponseEntity.ok(flashcardService.getAll());
    }

    @GetMapping("/sentence")
    public ResponseEntity<Sentence> retrieveSentence() {
        return ResponseEntity.ok(flashcardService.getSentence());
    }

    @PostMapping
    public ResponseEntity<List<Flashcard>> processSentence(@Valid @RequestBody Sentence sentence) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flashcardService.create(sentence));
    }

    @PutMapping
    public ResponseEntity<Flashcard> updateFlashcard(@Valid @RequestBody Flashcard flashcard) {
        flashcardService.update(flashcard);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Flashcard> removeFlashcard(@Valid @RequestBody Flashcard flashcard) {
        flashcardService.remove(flashcard);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportToCSV() throws IOException {
        MediaType mediaType = MediaType.parseMediaType("text/csv");
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(csvExporter.write(flashcardService.getAll()));
    }
}
