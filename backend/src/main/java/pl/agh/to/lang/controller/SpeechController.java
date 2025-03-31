package pl.agh.to.lang.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.to.lang.helpers.PartOfSentence;
import pl.agh.to.lang.helpers.PartOfSpeech;

import java.util.List;

@RestController
@RequestMapping("/api/speech")
public class SpeechController {
    @GetMapping
    public ResponseEntity<List<PartOfSpeech>> retrieveAllSpeechParts() {
        return ResponseEntity.ok(List.of(PartOfSpeech.values()));
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<PartOfSentence>> retrievePossiblePartsOfSentence(@PathVariable String name) {
        PartOfSpeech partOfSpeech = PartOfSpeech.valueOf(name.toUpperCase());
        return ResponseEntity.ok(partOfSpeech.getMatchingPartsOfSentence());
    }
}
