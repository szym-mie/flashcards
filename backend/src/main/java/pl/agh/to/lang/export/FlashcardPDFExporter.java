package pl.agh.to.lang.export;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import lombok.NoArgsConstructor;

import com.lowagie.text.Document;
import pl.agh.to.lang.model.Flashcard;
import pl.agh.to.lang.model.Sentence;
import pl.agh.to.lang.service.FlashcardService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

@NoArgsConstructor
public class FlashcardPDFExporter {
    private static class Builder {
        private final Document document;

        public Builder(Document document) {
            this.document = document;
            document.open();
        }

        public static Font defualtFont(int fontSize, int fontStyle) {
            return new Font(Font.HELVETICA, fontSize, fontStyle);
        }

        public static Font font(String fontFamily, int fontSize, int fontStyle) {
            int familyIndex = Font.getFamilyIndex(fontFamily);
            return new Font(familyIndex, fontSize, fontStyle);
        }

        @SafeVarargs
        public final Builder h1(String title, Consumer<Paragraph>... modifiers) {
            Font font = defualtFont(24, 0);
            Paragraph p = new Paragraph(title, font);
            applyModifiers(p, modifiers);
            document.add(p);
            return this;
        }

        @SafeVarargs
        public final Builder h3(String title, Consumer<Paragraph>... modifiers) {
            Font font = defualtFont(18, Font.ITALIC);
            Paragraph p = new Paragraph(title, font);
            applyModifiers(p, modifiers);
            document.add(p);
            return this;
        }

        @SafeVarargs
        public final Builder p(String text, Consumer<Paragraph>... modifiers) {
            Font font = defualtFont(12, 0);
            Paragraph p = new Paragraph(text, font);
            applyModifiers(p, modifiers);
            document.add(p);
            return this;
        }

        @SafeVarargs
        public final <E> Builder table(Collection<E> coll, TableBuilder<E> builder, Consumer<PdfPTable>... modifiers) {
            PdfPTable t = builder.build(coll);
            applyModifiers(t, modifiers);
            document.add(t);
            return this;
        }

        @SafeVarargs
        public final Builder hr(Consumer<LineSeparator>... modifiers) {
            LineSeparator ls = new LineSeparator();
            applyModifiers(ls, modifiers);
            document.add(ls);
            return this;
        }

        private static <E> void applyModifiers(E element, Consumer<E>[] modifiers) {
            Arrays.stream(modifiers).forEach(modifier -> modifier.accept(element));
        }

        public static Consumer<Paragraph> space(int before, int after) {
            return e -> {
                e.setSpacingBefore(before);
                e.setSpacingAfter(after);
            };
        }

        public static Consumer<Paragraph> align(String align) {
            return e -> e.setAlignment(align);
        }

        public static Consumer<Paragraph> indentLeft(int left) {
            return e -> e.setIndentationLeft(left);
        }
    }

    public byte[] export(FlashcardService service) throws IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, byteStream);

        TableBuilder<Flashcard> flashcardTableBuilder = new TableBuilder<Flashcard>()
                .header(Flashcard::getWord)
                .row(Flashcard::getLemma)
                .row(Flashcard::getTranslation)
                .row(Flashcard::getTranscription)
                .row(Flashcard::getPartOfSpeech)
                .row(Flashcard::getPartOfSentence)
                .replaceBlank("-");

        Sentence sentence = service.getSentence();

        try (document) {
            Builder documentBuilder = new Builder(document);
            documentBuilder
                    .h1("Fiszki", Builder.space(10, 10))
                    .hr()
                    .h3("Zdanie", Builder.space(10, 10))
                    .p(sentence.getText(), Builder.indentLeft(10))
                    .h3("Interlinia", Builder.space(10, 10))
                    .table(service.getAllOfSentence(sentence), flashcardTableBuilder)
                    .hr();
        } catch (DocumentException e) {
            throw new IOException(e);
        }

        return byteStream.toByteArray();
    }
}
