package pl.agh.to.lang.export;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.NoArgsConstructor;
import pl.agh.to.lang.service.FlashcardService;

import java.io.IOException;
import java.util.List;

@NoArgsConstructor
public class FlashcardCSVExporter {
    private final CsvSchema schema = CsvSchema.builder()
            .addColumn("word")
            .addColumn("lemma")
            .addColumn("translation")
            .addColumn("partOfSpeech")
            .addColumn("transcription")
            .build().withHeader();


    private final CsvMapper mapper = new CsvMapper();

    private ObjectWriter createObjectWriter() {
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        return mapper.writer(schema).forType(List.class);
    }

    public String export(FlashcardService service) throws IOException {
        return createObjectWriter().writeValueAsString(service.getAll());
    }
}
