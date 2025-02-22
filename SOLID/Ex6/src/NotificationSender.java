// ============================================================
// MODIFIED — NotificationSender (base class)
// ============================================================
// OLD:  public abstract void send(Notification n);
//       No contract enforcement. Each child did whatever it wanted:
//       - EmailSender truncated silently
//       - WhatsAppSender threw exceptions
//       - SmsSender ignored subject
//
// NEW:  Template Method Pattern (same as Ex5's Exporter).
//       send() is FINAL — handles null check centrally.
//       Children override doSend() — they get guaranteed non-null input.
//       Return type changed: void → SendResult (so failures are values, not exceptions).
// ============================================================

public abstract class NotificationSender {
    protected final AuditLog audit;

    protected NotificationSender(AuditLog audit) { this.audit = audit; }

    // FINAL = children cannot override. Contract enforced HERE.
    public final SendResult send(Notification n) {
        if (n == null) {
            return SendResult.fail("notification must not be null");
        }
        return doSend(n);
    }

    // Children override THIS.
    protected abstract SendResult doSend(Notification n);
}
