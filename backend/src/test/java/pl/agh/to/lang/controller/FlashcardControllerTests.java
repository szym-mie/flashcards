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
import pl.agh.to.lang.repository.FlashcardRepository;
import pl.agh.to.lang.service.TextProcessorService;

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
    private TextProcessorService textProcessorService;

    @MockitoBean
    private FlashcardRepository flashcardRepository;

    @Test
    void testRetrieveAllFlashcards() throws Exception {
        Flashcard flashcard1 = new Flashcard("hello");
        flashcard1.setTranslation("cześć");
        Flashcard flashcard2 = new Flashcard("world");
        flashcard2.setTranslation("świat");

        Mockito.when(flashcardRepository.getAll())
                .thenReturn(List.of(flashcard1, flashcard2));

        mvc.perform(MockMvcRequestBuilders.get("/api/flashcards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].word").value("hello"))
                .andExpect(jsonPath("$[0].translation").value("cześć"))
                .andExpect(jsonPath("$[1].word").value("world"))
                .andExpect(jsonPath("$[1].translation").value("świat"));
    }

    @Test
    void testProcessSentence() throws Exception {
        Mockito.when(textProcessorService.extractWords(Mockito.any()))
                .thenCallRealMethod();

        Mockito.doNothing().when(flashcardRepository).add(Mockito.any());

        mvc.perform(MockMvcRequestBuilders.post("/api/flashcards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": \"example text\", \"direction\": \"LTR\"}"))
                .andExpect(status().isNoContent());

        Mockito.verify(flashcardRepository, Mockito.times(2)).add(Mockito.any());
    }

    @Test
    void testTranslateFlashcard() throws Exception {
        Mockito.doNothing().when(flashcardRepository).updateByWord(Mockito.anyString(), Mockito.anyString());

        mvc.perform(MockMvcRequestBuilders.put("/api/flashcards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"word\": \"hello\", \"translation\": \"cześć\"}"))
                .andExpect(status().isNoContent());

        Mockito.verify(flashcardRepository).updateByWord("hello", "cześć");
    }

    @Test
    void testRemoveFlashcard() throws Exception {
        Mockito.doNothing().when(flashcardRepository).removeByWord(Mockito.anyString());

        mvc.perform(MockMvcRequestBuilders.delete("/api/flashcards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"word\": \"hello\", \"translation\": \"cześć\"}"))
                .andExpect(status().isNoContent());
        Mockito.verify(flashcardRepository).removeByWord("hello");
    }

    @Test
    void testCSVExport() throws Exception {
        Flashcard flashcard1 = new Flashcard("Peter");
        flashcard1.setTranslation("Piotr");
        Flashcard flashcard2 = new Flashcard("John");
        flashcard2.setTranslation("Jan");

        Mockito.when(flashcardRepository.getAll())
                .thenReturn(List.of(flashcard1, flashcard2));

        mvc.perform(MockMvcRequestBuilders.get("/api/flashcards/export")
                        .contentType("text/csv"))
                .andExpect(status().isOk())
                .andExpect(content().string("word,translation\nPeter,Piotr\nJohn,Jan\n"));
    }
}
