package pl.agh.to.lang.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.to.lang.model.TranslationRequest;
import pl.agh.to.lang.model.WordTranslation;
import pl.agh.to.lang.service.TextProcessorService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FlashcardController {
    private final TextProcessorService textProcessorService;

    private final Map<String, String> translations = new HashMap<>();

    public FlashcardController(TextProcessorService textProcessorService) {
        this.textProcessorService = textProcessorService;
    }

    @PostMapping("/text")
    public ResponseEntity<Map<String, List<String>>> processText(@RequestBody TranslationRequest request) {
        List<String> words = textProcessorService.extractWords(request.getText(), request.getDirection());
        return ResponseEntity.ok(Collections.singletonMap("words", words));
    }

    @PostMapping("/translation")
    public ResponseEntity<String> saveTranslations(@RequestBody WordTranslation translation) {
        translations.put(translation.getWord(), translation.getTranslation());
        return ResponseEntity.ok("Translations saved successfully!");
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"flashcards.csv\"");

        try (PrintWriter writer = response.getWriter()) {
            writer.println("Word,Translation");
            translations.forEach((word, translation) -> writer.println(word + "," + translation));
        }
    }
}
