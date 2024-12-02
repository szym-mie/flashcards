package pl.agh.to.lang.controller;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.to.lang.model.Flashcard;
import pl.agh.to.lang.dto.SentenceRequest;
import pl.agh.to.lang.dto.TranslationRequest;
import pl.agh.to.lang.service.FlashcardService;
import pl.agh.to.lang.service.TextProcessorService;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

@RestController
@RequestMapping("/api/flashcards")
@AllArgsConstructor
public class FlashcardController {
    private final TextProcessorService textProcessorService;

    private final FlashcardService flashcardService;

    @GetMapping
    public ResponseEntity<List<Flashcard>> retrieveAllFlashcards() {
        return ResponseEntity.ok(flashcardService.getAll());
    }

    @PostMapping
    public ResponseEntity<String> processSentence(@Valid @RequestBody SentenceRequest sentenceRequest) {
        List<String> words = textProcessorService.extractWords(sentenceRequest);
        words.forEach(flashcardService::add);

        return ResponseEntity.ok("Successfully processed!");
    }

    @PutMapping("/{word}")
    public ResponseEntity<String> translateFlashcard(
            @PathVariable String word,
            @Valid @RequestBody TranslationRequest translationRequest
    ) {
        flashcardService.update(word, translationRequest.getText());

        return ResponseEntity.ok("Successfully translated!");
    }

    @DeleteMapping("/{word}")
    public ResponseEntity<String> removeFlashcard(@PathVariable String word) {
        flashcardService.remove(word);

        return ResponseEntity.ok("Successfully removed!");
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportToCSV() throws IOException {
        CsvSchema schema = CsvSchema.builder()
                .addColumn("word")
                .addColumn("translation")
                .build().withHeader();

        CsvMapper mapper = new CsvMapper();
        StringWriter stringWriter = new StringWriter();
        ObjectWriter csvWriter = mapper.writer(schema).forType(Flashcard.class);

        for (Flashcard flashcard : flashcardService.getAll())
            csvWriter.writeValue(stringWriter, flashcard);

        MediaType mediaType = MediaType.parseMediaType("text/csv");
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(stringWriter.toString());
    }
}
