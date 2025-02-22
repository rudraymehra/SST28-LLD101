// ============================================================
// NEW CLASS — SendResult
// ============================================================
// WHAT:  A simple result object — either success or failure with a reason.
// WHY:   OLD code: WhatsAppSender THREW exceptions on failure.
//        That's a surprise — caller needs try/catch specifically for WhatsApp.
//        NEW: All senders RETURN SendResult. No exceptions. Predictable.
//        Same pattern as ExportResult in Ex5.
// ============================================================

public class SendResult {
    public final boolean success;
    public final String error;

    private SendResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public static SendResult ok() { return new SendResult(true, null); }
    public static SendResult fail(String error) { return new SendResult(false, error); }
}
