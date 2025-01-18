package pl.agh.to.lang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.agh.to.lang.model.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {
}
