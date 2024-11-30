package pl.agh.to.lang.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.to.lang.model.Flashcard;
import pl.agh.to.lang.model.SentenceRequest;
import pl.agh.to.lang.model.TranslationRequest;
import pl.agh.to.lang.service.FlashcardService;
import pl.agh.to.lang.service.TextProcessorService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {
    private final TextProcessorService textProcessorService;

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService, TextProcessorService textProcessorService) {
        this.flashcardService = flashcardService;
        this.textProcessorService = textProcessorService;
    }

    @GetMapping
    public ResponseEntity<List<Flashcard>> retrieveAllFlashcards() {
        return ResponseEntity.ok(flashcardService.getAll());
    }

    @PostMapping
    public ResponseEntity<String> processSentence(@Valid @RequestBody SentenceRequest sentenceRequest) {
        List<String> words = textProcessorService.extractWords(sentenceRequest.getText(), sentenceRequest.getDirection());
        words.forEach(flashcardService::add);

        return ResponseEntity.ok("Successfully processed!");
    }

    @PutMapping("/{word}")
    public ResponseEntity<String> translateFlashcard(@PathVariable String word, @Valid @RequestBody TranslationRequest translationRequest) {
        flashcardService.update(word, translationRequest.getText());

        return ResponseEntity.ok("Successfully translated!");
    }

    @DeleteMapping("/{word}")
    public ResponseEntity<String> removeFlashcard(@PathVariable String word) {
        flashcardService.remove(word);

        return ResponseEntity.ok("Successfully removed!");
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"flashcards.csv\"");

        try (PrintWriter writer = response.getWriter()) {
            writer.println("Word,Translation");
            flashcardService.getAll().forEach(flashcard -> writer.println(flashcard.getWord() + "," + flashcard.getTranslation()));
        }
    }
}
