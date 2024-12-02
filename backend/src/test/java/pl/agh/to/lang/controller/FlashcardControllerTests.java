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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.agh.to.lang.model.Flashcard;
import pl.agh.to.lang.service.FlashcardService;
import pl.agh.to.lang.service.TextProcessorService;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FlashcardController.class)
public class FlashcardControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private TextProcessorService textProcessorService;

    @MockitoBean
    private FlashcardService flashcardService;

    @Test
    public void testRetrieveAllFlashcards() throws Exception {
        Mockito.when(flashcardService.getAll()).thenReturn(List.of(
                new Flashcard("hello", "cześć"),
                new Flashcard("world", "świat")
        ));

        mvc.perform(MockMvcRequestBuilders.get("/api/flashcards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].word").value("hello"))
                .andExpect(jsonPath("$[0].translation").value("cześć"))
                .andExpect(jsonPath("$[1].word").value("world"))
                .andExpect(jsonPath("$[1].translation").value("świat"));
    }

    @Test
    public void testProcessSentence() throws Exception {
        Mockito.when(textProcessorService.extractWords(Mockito.any())).thenReturn(List.of("word1", "word2"));
        Mockito.doNothing().when(flashcardService).add(Mockito.any());

        mvc.perform(MockMvcRequestBuilders.post("/api/flashcards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": \"example text\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully processed!"));

        Mockito.verify(flashcardService, Mockito.times(2)).add(Mockito.anyString());
    }

    @Test
    public void testTranslateFlashcard() throws Exception {
        Mockito.doNothing().when(flashcardService).update(Mockito.anyString(), Mockito.anyString());

        mvc.perform(MockMvcRequestBuilders.put("/api/flashcards/hello")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": \"cześć\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully translated!"));

        Mockito.verify(flashcardService).update("hello", "cześć");
    }

    @Test
    public void testRemoveFlashcard() throws Exception {
        Mockito.doNothing().when(flashcardService).remove(Mockito.anyString());

        mvc.perform(MockMvcRequestBuilders.delete("/api/flashcards/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully removed!"));

        Mockito.verify(flashcardService).remove("hello");
    }

    private ResultActions request(String url, String contentType) throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
                .contentType(contentType);
        return mvc.perform(requestBuilder);
    }

    @Test
    public void testCSVExport() throws Exception {
        Mockito.when(flashcardService.getAll()).thenReturn(List.of(
                new Flashcard("Peter", "Piotr")
        ));

        request("/api/flashcards/export", "text/csv")
                .andExpect(status().isOk())
                .andExpect(content().string("word,translation\nPeter,Piotr\n"));
    }
}
