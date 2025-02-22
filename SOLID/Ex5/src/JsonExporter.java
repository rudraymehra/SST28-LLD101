import java.nio.charset.StandardCharsets;

// ============================================================
// MODIFIED — JsonExporter
// ============================================================
// OLD:  Overrode export(). Had its own null checks:
//       if (req == null) return empty result;   ← inconsistent with others
//       if (s == null) return "";                ← redundant null handling
//
// NEW:  Overrides doExport(). No null checks needed anywhere — base class
//       guarantees req, title, and body are all non-null.
// ============================================================

public class JsonExporter extends Exporter {
    @Override
    protected ExportResult doExport(ExportRequest req) {
        String json = "{\"title\":\"" + escape(req.title) + "\",\"body\":\"" + escape(req.body) + "\"}";
        return new ExportResult("application/json", json.getBytes(StandardCharsets.UTF_8));
    }

    private String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}
