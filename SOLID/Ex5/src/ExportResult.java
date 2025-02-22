// ============================================================
// MODIFIED — ExportResult
// ============================================================
// OLD:  Only had contentType + bytes. If something failed,
//       PdfExporter THREW an exception. Caller needed try/catch.
//
// NEW:  Added error field + fail() factory + isSuccess() method.
//       Now failures are RETURNED as values, not thrown as exceptions.
//       This is the "Result pattern" — much safer and predictable.
//
// WHY:  Exceptions are SURPRISES. LSP says children shouldn't surprise
//       the caller. Returning ExportResult.fail("reason") is predictable.
// ============================================================

public class ExportResult {
    public final String contentType;
    public final byte[] bytes;
    public final String error;

    // Success constructor (same as before)
    public ExportResult(String contentType, byte[] bytes) {
        this.contentType = contentType;
        this.bytes = bytes;
        this.error = null;
    }

    // Failure constructor (private — use fail() instead)
    private ExportResult(String error) {
        this.contentType = null;
        this.bytes = new byte[0];
        this.error = error;
    }

    // Factory method for failures
    public static ExportResult fail(String message) {
        return new ExportResult(message);
    }

    public boolean isSuccess() {
        return error == null;
    }
}
