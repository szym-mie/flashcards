package pl.agh.to.lang.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.agh.to.lang.dto.FlashcardsResponse;
import pl.agh.to.lang.model.Flashcard;
import pl.agh.to.lang.model.Sentence;
import pl.agh.to.lang.repository.FlashcardRepository;
import pl.agh.to.lang.repository.SentenceRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class FlashcardService {
    private final TextProcessorService textProcessorService;

    private final FlashcardRepository flashcardRepository;

    private SentenceRepository sentenceRepository;

    public List<Flashcard> getAll() {
        return flashcardRepository.findAll();
    }

    public Sentence getSentence() {
        return sentenceRepository.findOne().orElseThrow();
    }

    public Flashcard getByWordOrThrow(String word) {
        return flashcardRepository.findByWord(word).orElseThrow();
    }

    public List<Flashcard> create(Sentence sentence) {
        sentenceRepository.save(sentence);

        List<String> wordList = textProcessorService.extractWords(sentence.getText());
        wordList.stream()
                .map(Flashcard::new)
                .forEach(flashcardRepository::save);

        return getAll();
    }

    public void update(Flashcard flashcard) {
        //ensure that flashcard exist
        getByWordOrThrow(flashcard.getWord());

        flashcardRepository.update(flashcard);
    }

    public void remove(Flashcard flashcard) {
        //ensure that flashcard exist
        Flashcard foundFlashcard = getByWordOrThrow(flashcard.getWord());

        flashcardRepository.delete(foundFlashcard);
    }
}
