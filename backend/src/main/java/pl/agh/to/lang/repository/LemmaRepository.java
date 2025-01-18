package pl.agh.to.lang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.agh.to.lang.model.Language;
import pl.agh.to.lang.model.Lemma;

import java.util.Optional;

@Repository
public interface LemmaRepository extends JpaRepository<Lemma, Long> {
    @Query("SELECT l FROM Lemma l WHERE l.name = :name AND l.language = :language")
    Optional<Lemma> findOneByNameAndLanguage(@Param("name") String name, @Param("language") Language language);
}
