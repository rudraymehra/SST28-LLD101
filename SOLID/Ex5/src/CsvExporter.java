import java.nio.charset.StandardCharsets;

// ============================================================
// MODIFIED — CsvExporter
// ============================================================
// OLD:  Overrode export(). Had its own null check: req.body == null ? "" : ...
//       Every child was doing its OWN null handling differently.
//
// NEW:  Overrides doExport(). No null check needed — base class guarantees
//       body is never null. Cleaner code, consistent behavior.
// ============================================================

public class CsvExporter extends Exporter {
    @Override
    protected ExportResult doExport(ExportRequest req) {
        String body = req.body.replace("\n", " ").replace(",", " ");
        String csv = "title,body\n" + req.title + "," + body + "\n";
        return new ExportResult("text/csv", csv.getBytes(StandardCharsets.UTF_8));
    }
}
