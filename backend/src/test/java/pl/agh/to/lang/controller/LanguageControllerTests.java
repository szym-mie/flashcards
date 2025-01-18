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
import pl.agh.to.lang.service.LanguageService;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LanguageController.class)
class LanguageControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private LanguageService languageService;

    @Test
    void testRetrieveAllLanguages() throws Exception {
        Language language1 = new Language("en", "English");
        Language language2 = new Language("fr", "French");
        Mockito.when(languageService.getAll()).thenReturn(List.of(language1, language2));

        mvc.perform(MockMvcRequestBuilders.get("/api/languages")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"en\",\"name\":\"English\"},{\"id\":\"fr\",\"name\":\"French\"}]"));

        Mockito.verify(languageService).getAll();
    }

    @Test
    void testCreateLanguage() throws Exception {
        Language language = new Language("es", "Spanish");
        Mockito.when(languageService.create(Mockito.any())).thenReturn(language);

        mvc.perform(MockMvcRequestBuilders.post("/api/languages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"es\", \"name\": \"Spanish\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":\"es\",\"name\":\"Spanish\"}"));

        Mockito.verify(languageService).create(Mockito.any());
    }

    @Test
    void testUpdateLanguage() throws Exception {
        Mockito.doNothing().when(languageService).update(Mockito.any());

        mvc.perform(MockMvcRequestBuilders.put("/api/languages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"en\", \"name\": \"English Updated\"}"))
                .andExpect(status().isNoContent());

        Mockito.verify(languageService).update(Mockito.any());
    }
}
