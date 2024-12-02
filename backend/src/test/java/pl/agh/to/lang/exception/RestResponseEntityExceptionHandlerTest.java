package pl.agh.to.lang.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.to.lang.handler.RestResponseEntityExceptionHandler;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {TestController.class, RestResponseEntityExceptionHandler.class})
class RestResponseEntityExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHandleIOException() throws Exception {
        mockMvc.perform(get("/test/ioexception"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("IO exception occurred"));
    }
}

@RestController
class TestController {

    @GetMapping("/test/ioexception")
    public void throwIOException() throws IOException {
        throw new IOException("Simulated IO exception");
    }
}
