package pl.agh.to.lang.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.agh.to.lang.model.Language;
import pl.agh.to.lang.model.Lemma;
import pl.agh.to.lang.repository.LemmaRepository;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(LemmaController.class)
class LemmaControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private LemmaRepository lemmaRepository;

    @Test
    void testRetrieveLemmaByNameAndLanguage() throws Exception {
        Language language = new Language("en", "English");
        Lemma lemma = new Lemma("run", "bieg", language);

        Mockito.when(lemmaRepository.findOneByNameAndLanguage(Mockito.eq("run"), Mockito.any(Language.class)))
                .thenReturn(Optional.of(lemma));

        mvc.perform(MockMvcRequestBuilders.get("/api/lemmas/run")
                        .param("language.id", "en")
                        .param("language.name", "English")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"run\",\"translation\":\"bieg\",\"language\":{\"id\":\"en\",\"name\":\"English\"}}"));

        Mockito.verify(lemmaRepository).findOneByNameAndLanguage(Mockito.eq("run"), Mockito.any(Language.class));
    }

    @Test
    void testRetrieveLemmaByNameAndLanguageNotFound() throws Exception {
        Mockito.when(lemmaRepository.findOneByNameAndLanguage(Mockito.eq("unknown"), Mockito.any(Language.class)))
                .thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/api/lemmas/unknown")
                        .param("language.id", "en")
                        .param("language.name", "English")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(lemmaRepository).findOneByNameAndLanguage(Mockito.eq("unknown"), Mockito.any(Language.class));
    }

    @Test
    void testUpsertLemma() throws Exception {
        Language language = new Language("en", "English");
        Lemma lemma = new Lemma("run", "bieg", language);

        Mockito.when(lemmaRepository.save(Mockito.any())).thenReturn(lemma);

        mvc.perform(MockMvcRequestBuilders.post("/api/lemmas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"run\", \"translation\": \"bieg\", \"language\": {\"id\": \"en\", \"name\": \"English\"}}"))
                .andExpect(status().isNoContent());

        Mockito.verify(lemmaRepository).save(Mockito.any());
    }
}
