package pl.agh.to.lang.controller;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.to.lang.model.Flashcard;
import pl.agh.to.lang.dto.SentenceRequest;
import pl.agh.to.lang.repository.FlashcardRepository;
import pl.agh.to.lang.service.TextProcessorService;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
@AllArgsConstructor
public class FlashcardController {
    private final TextProcessorService textProcessorService;

    private final FlashcardRepository flashcardRepository;

    @GetMapping
    public ResponseEntity<List<Flashcard>> retrieveAllFlashcards() {
        return ResponseEntity.ok(flashcardRepository.getAll());
    }

    @PostMapping
    public ResponseEntity<List<Flashcard>> processSentence(@Valid @RequestBody SentenceRequest sentenceRequest) {
        List<String> wordList = textProcessorService.extractWords(sentenceRequest);
        List<Flashcard> flashcardList = wordList.stream()
                .map(Flashcard::new)
                .toList();

        flashcardRepository.addAll(flashcardList);

        return ResponseEntity.ok(flashcardList);
    }

    @PutMapping
    public ResponseEntity<Flashcard> translateFlashcard(@RequestBody Flashcard flashcard) {
        flashcardRepository.updateByWord(flashcard.getWord(), flashcard.getTranslation());

        return ResponseEntity.ok(flashcard);
    }

    @DeleteMapping
    public ResponseEntity<Flashcard> removeFlashcard(@RequestBody Flashcard flashcard) {
        flashcardRepository.removeByWord(flashcard.getWord());

        return ResponseEntity.ok(flashcard);
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportToCSV() throws IOException {
        CsvSchema schema = CsvSchema.builder()
                .addColumn("word")
                .addColumn("translation")
                .build().withHeader();

        CsvMapper mapper = new CsvMapper();
        StringWriter stringWriter = new StringWriter();
        ObjectWriter csvWriter = mapper.writer(schema).forType(List.class);
        csvWriter.writeValue(stringWriter, flashcardRepository.getAll());

        MediaType mediaType = MediaType.parseMediaType("text/csv");
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(stringWriter.toString());
    }
}
