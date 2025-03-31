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
import pl.agh.to.lang.model.Flashcard;
import pl.agh.to.lang.helpers.PartOfSpeech;
import pl.agh.to.lang.service.FlashcardService;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FlashcardController.class)
class FlashcardControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private FlashcardService flashcardService;

    @Autowired
    private FlashcardController flashcardController;

    @Test
    void testRetrieveAllFlashcards() throws Exception {
        Flashcard flashcard1 = new Flashcard("hello");
        flashcard1.setTranslation("cześć");
        Flashcard flashcard2 = new Flashcard("world");
        flashcard2.setTranslation("świat");

        Mockito.when(flashcardService.getAll()).thenReturn(List.of(flashcard1, flashcard2));

        mvc.perform(MockMvcRequestBuilders.get("/api/flashcards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].word").value("hello"))
                .andExpect(jsonPath("$[0].translation").value("cześć"))
                .andExpect(jsonPath("$[1].word").value("world"))
                .andExpect(jsonPath("$[1].translation").value("świat"));
    }

    @Test
    void testProcessSentence() throws Exception {
        Mockito.when(flashcardService.create(Mockito.any())).thenReturn(List.of(new Flashcard("example")));

        mvc.perform(MockMvcRequestBuilders.post("/api/flashcards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": \"example text\", \"language\": {\"id\": \"en\", \"name\": \"English\"}}"))
                .andExpect(status().isCreated());

        Mockito.verify(flashcardService, Mockito.atLeastOnce()).create(Mockito.any());
    }


    @Test
    void testTranslateFlashcard() throws Exception {
        Mockito.doNothing().when(flashcardService).update(Mockito.any());

        mvc.perform(MockMvcRequestBuilders.put("/api/flashcards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"word\": \"hello\", \"lemma\": \"lemma-example\", \"translation\": \"cześć\", \"partOfSpeech\": \"NOUN\", \"partOfSentence\": \"OBJECT\", \"transcription\": \"he-lo\"}"))
                .andExpect(status().isNoContent());

        Mockito.verify(flashcardService).update(Mockito.any());
    }

    @Test
    void testRemoveFlashcard() throws Exception {
        Mockito.doNothing().when(flashcardService).remove(Mockito.any());

        mvc.perform(MockMvcRequestBuilders.delete("/api/flashcards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"word\": \"hello\", \"lemma\": \"lemma-example\", \"translation\": \"cześć\", \"partOfSpeech\": \"NOUN\", \"partOfSentence\": \"OBJECT\", \"transcription\": \"he-lo\"}"))
                .andExpect(status().isNoContent());

        Mockito.verify(flashcardService).remove(Mockito.any());
    }

    @Test
    void testCSVExport() throws Exception {
        Flashcard flashcard1 = new Flashcard("Peter");
        flashcard1.setTranslation("Piotr");
        Flashcard flashcard2 = new Flashcard("John");
        flashcard2.setTranslation("Jan");
        flashcard2.setPartOfSpeech(PartOfSpeech.NOUN);

        Mockito.when(flashcardService.getAll()).thenReturn(List.of(flashcard1, flashcard2));

        mvc.perform(MockMvcRequestBuilders.get("/api/flashcards/export_csv")
                        .accept("text/csv"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "word,lemma,translation,partOfSpeech,transcription\nPeter,,Piotr,,\nJohn,,Jan,NOUN,\n"
                ));
    }

}
