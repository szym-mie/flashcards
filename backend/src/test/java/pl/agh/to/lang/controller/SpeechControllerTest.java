package pl.agh.to.lang.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SpeechController.class)
class SpeechControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void testGetAllSpeechParts() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/speech"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[\"NOUN\",\"VERB\",\"ADJECTIVE\",\"ADVERB\",\"NUMERAL\",\"PRONOUN\",\"PREPOSITION\",\"CONJUNCTION\",\"PARTICLE\",\"INTERJECTION\",\"ARTICLE\",\"OTHER\"]"
                ));
    }

    @Test
    void testGetPossiblePartsOfSentence() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/speech/noun"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[\"SUBJECT\",\"OBJECT\",\"ADVERBIAL_MODIFIER\",\"ATTRIBUTE\",\"OTHER\"]"
                ));

        mvc.perform(MockMvcRequestBuilders.get("/api/speech/aRtIcLe"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[\"OTHER\"]"
                ));

        mvc.perform(MockMvcRequestBuilders.get("/api/speech/gggggg"))
                .andExpect(status().isBadRequest());
    }
}