// ============================================================
// MODIFIED — WhatsAppSender
// ============================================================
// OLD:  THREW IllegalArgumentException if phone didn't start with "+".
//       That's an LSP violation — parent never said "I might throw."
//       Caller needed try/catch ONLY for WhatsApp = can't treat all senders equally.
//
// NEW:  RETURNS SendResult.fail() instead of throwing.
//       Caller just checks waResult.success — no try/catch needed.
//       NOW you can loop over all senders uniformly. That's LSP!
// ============================================================

public class WhatsAppSender extends NotificationSender {
    public WhatsAppSender(AuditLog audit) { super(audit); }

    @Override
    protected SendResult doSend(Notification n) {
        if (n.phone == null || !n.phone.startsWith("+")) {
            return SendResult.fail("phone must start with + and country code");
        }
        System.out.println("WA -> to=" + n.phone + " body=" + n.body);
        audit.add("wa sent");
        return SendResult.ok();
    }
}
