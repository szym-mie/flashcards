package pl.agh.to.lang.repository;

import org.springframework.stereotype.Repository;
import pl.agh.to.lang.model.Sentence;

import java.util.Optional;

@Repository
public class SentenceRepository {
    private Sentence sentence;

    public Optional<Sentence> findOne() {
        return Optional.ofNullable(sentence);
    }

    public void save(Sentence sentence) {
        this.sentence = sentence;
    }
}
