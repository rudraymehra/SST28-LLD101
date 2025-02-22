import java.nio.charset.StandardCharsets;

// ============================================================
// MODIFIED — PdfExporter
// ============================================================
// OLD:  Overrode export(). THREW IllegalArgumentException if body > 20 chars.
//       That's an LSP violation — parent never said "I might throw for long text."
//       Caller had to wrap in try/catch just for PDF. Surprise!
//
// NEW:  Overrides doExport(). RETURNS ExportResult.fail() instead of throwing.
//       No surprise. Caller just checks result.isSuccess().
//       Also: no null check on body — base class guarantees body is never null.
// ============================================================

public class PdfExporter extends Exporter {
    @Override
    protected ExportResult doExport(ExportRequest req) {
        if (req.body.length() > 20) {
            return ExportResult.fail("PDF cannot handle content > 20 chars");
        }
        String fakePdf = "PDF(" + req.title + "):" + req.body;
        return new ExportResult("application/pdf", fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}
