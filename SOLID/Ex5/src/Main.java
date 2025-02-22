public class Main {
    public static void main(String[] args) {
        System.out.println("=== Export Demo ===");

        ExportRequest req = new ExportRequest("Weekly Report", SampleData.longBody());
        Exporter pdf = new PdfExporter();
        Exporter csv = new CsvExporter();
        Exporter json = new JsonExporter();

        // ===== OLD: needed try/catch because PdfExporter THREW exceptions =====
        // System.out.println("PDF: " + safe(pdf, req));
        // private static String safe(Exporter e, ExportRequest r) {
        //     try { ... } catch (RuntimeException ex) { return "ERROR: " + ex.getMessage(); }
        // }

        // ===== NEW: no try/catch needed! Just check result.isSuccess() =====
        // Every exporter now returns a result — never throws. Predictable.
        System.out.println("PDF: " + format(pdf.export(req)));
        System.out.println("CSV: " + format(csv.export(req)));
        System.out.println("JSON: " + format(json.export(req)));
    }

    private static String format(ExportResult result) {
        if (result.isSuccess()) {
            return "OK bytes=" + result.bytes.length;
        }
        return "ERROR: " + result.error;
    }
}
