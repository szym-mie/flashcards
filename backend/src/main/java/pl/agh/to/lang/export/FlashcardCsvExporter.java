package pl.agh.to.lang.export;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.NoArgsConstructor;
import pl.agh.to.lang.model.Flashcard;

import java.io.IOException;
import java.util.List;

@NoArgsConstructor
public class FlashcardCsvExporter {
    private final CsvSchema schema = CsvSchema.builder()
            .addColumn("word")
            .addColumn("lemma")
            .addColumn("translation")
            .addColumn("partOfSpeech")
            .addColumn("transcription")
            .build().withHeader();

    private final CsvMapper mapper = new CsvMapper();

    private ObjectWriter createObjectWriter() {
        return mapper.writer(schema).forType(List.class);
    }

    public String write(List<Flashcard> flashcardList) throws IOException {
        return createObjectWriter().writeValueAsString(flashcardList);
    }
}
