package pl.agh.to.lang.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.agh.to.lang.dto.Phrase;
import pl.agh.to.lang.helpers.PartOfSentence;
import pl.agh.to.lang.helpers.PhraseType;
import pl.agh.to.lang.model.Flashcard;
import pl.agh.to.lang.model.Sentence;
import pl.agh.to.lang.repository.FlashcardRepository;
import pl.agh.to.lang.repository.SentenceRepository;

import java.util.List;
import java.util.Optional;

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

    public List<Phrase> getSentencePhrases() {
        return textProcessorService.extractPhrases(getSentence().getText())
                .stream()
                .map(phrase -> {
                    List<String> phraseWords = textProcessorService.extractWords(phrase);
                    boolean isCoordinate = phraseWords
                            .stream()
                            .anyMatch(word -> flashcardRepository.findByWord(word).filter(value -> value.getPartOfSentence() == PartOfSentence.COORDINATE).isPresent());
                    boolean isSubordinate = phraseWords
                            .stream()
                            .anyMatch(word -> flashcardRepository.findByWord(word).filter(value -> value.getPartOfSentence() == PartOfSentence.SUBORDINATE).isPresent());
                    return new Phrase(phrase, isCoordinate && isSubordinate ? null : isCoordinate ? PhraseType.COORDINATE : isSubordinate ? PhraseType.SUBORDINATE : null);
                })
                .toList();
    }

    public List<Flashcard> getAllOfSentence(Sentence sentence) {
        return textProcessorService.extractWords(sentence.getText())
                .stream()
                .map(flashcardRepository::findByWord)
                .map(Optional::orElseThrow)
                .toList();
    }

    public Flashcard getByWordOrThrow(String word) {
        return flashcardRepository.findByWord(word).orElseThrow();
    }

    public List<Flashcard> create(Sentence sentence) {
        sentenceRepository.save(sentence);

        textProcessorService.extractWords(sentence.getText())
                .stream()
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
