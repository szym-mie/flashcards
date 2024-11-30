package pl.agh.to.lang.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FlashcardController.class)
public class FlashcardControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private TextProcessorService textProcessorService;

    @MockitoBean
    private FlashcardService flashcardService;

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
