package pl.agh.to.lang.export;

import com.lowagie.text.pdf.PdfPTable;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor
public class TableBuilder<E> {
    private Function<E, Object> headerMapper;
    private String blankSymbol;
    private final List<Function<E, Object>> rowMappers = new LinkedList<>();

    public TableBuilder<E> header(Function<E, Object> headerMapper) {
        this.headerMapper = headerMapper;
        return this;
    }

    public TableBuilder<E> row(Function<E, Object> rowMapper) {
        this.rowMappers.add(rowMapper);
        return this;
    }

    public TableBuilder<E> replaceBlank(String blankSymbol) {
        this.blankSymbol = blankSymbol;
        return this;
    }

    public PdfPTable build(Collection<E> coll) {
        PdfPTable table = new PdfPTable(coll.size());
        table.setHeaderRows(1);
        coll.stream()
                .map(this.headerMapper)
                .map(this::formatAny)
                .forEach(table::addCell);

        this.rowMappers.stream()
                .flatMap(rowMapper -> coll.stream().map(rowMapper))
                .map(this::formatAny)
                .forEach(table::addCell);

        return table;
    }

    private String formatAny(Object obj) {
        String text = obj != null ? obj.toString() : "";
        return !text.isBlank() ? text : this.blankSymbol;
    }
}
