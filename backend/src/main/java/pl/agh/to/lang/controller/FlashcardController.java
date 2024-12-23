package pl.agh.to.lang.controller;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
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
import pl.agh.to.lang.model.Flashcard;
import pl.agh.to.lang.model.Sentence;
import pl.agh.to.lang.service.FlashcardService;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
@RequiredArgsConstructor
public class FlashcardController {
    private final FlashcardService flashcardService;

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
        CsvSchema schema = CsvSchema.builder()
                .addColumn("word")
                .addColumn("lemma")
                .addColumn("translation")
                .addColumn("partOfSpeech")
                .addColumn("transcription")
                .build().withHeader();

        CsvMapper mapper = new CsvMapper();
        StringWriter stringWriter = new StringWriter();
        ObjectWriter csvWriter = mapper.writer(schema).forType(List.class);
        csvWriter.writeValue(stringWriter, flashcardService.getAll());

        MediaType mediaType = MediaType.parseMediaType("text/csv");
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(stringWriter.toString());
    }
}
