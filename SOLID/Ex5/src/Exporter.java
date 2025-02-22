// ============================================================
// MODIFIED — Exporter (base class)
// ============================================================
// OLD:  public abstract ExportResult export(ExportRequest req);
//       That's it. No validation, no contract. Every child did its own
//       thing — PdfExporter threw exceptions, JsonExporter handled null
//       differently, CsvExporter silently corrupted data.
//
// NEW:  Template Method Pattern.
//       export() is now FINAL — children CANNOT override it.
//       It handles ALL the common validation (null checks, body normalization).
//       Children override doExport() instead — they get CLEAN, safe input.
//
// WHY:  LSP says "if parent promises something, children must honor it."
//       By making export() final, we ENFORCE the contract in ONE place.
//       No child can break the rules.
// ============================================================

public abstract class Exporter {

    // FINAL = children cannot override this. Contract is enforced HERE.
    public final ExportResult export(ExportRequest req) {
        // Common validation — done ONCE, not repeated in every child
        if (req == null || req.title == null) {
            return ExportResult.fail("request and title must not be null");
        }
        // Normalize null body to "" — so children never see null
        String body = (req.body == null) ? "" : req.body;
        return doExport(new ExportRequest(req.title, body));
    }

    // Children override THIS method. They get guaranteed non-null input.
    protected abstract ExportResult doExport(ExportRequest req);
}
