package pl.agh.to.lang.export;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import pl.agh.to.lang.model.Flashcard;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class FlashcardCsvExporter {
    private final StringWriter stringWriter;
    private final ObjectWriter csvWriter;

    public FlashcardCsvExporter() {
        CsvSchema schema = CsvSchema.builder()
                .addColumn("word")
                .addColumn("lemma")
                .addColumn("translation")
                .addColumn("partOfSpeech")
                .addColumn("transcription")
                .build().withHeader();

        CsvMapper mapper = new CsvMapper();
        stringWriter = new StringWriter();
        csvWriter = mapper.writer(schema).forType(List.class);
    }

    public String write(List<Flashcard> flashcardList) throws IOException {
        csvWriter.writeValue(stringWriter, flashcardList);
        return stringWriter.toString();
    }
}
