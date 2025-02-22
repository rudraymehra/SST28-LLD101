// ============================================================
// MODIFIED — EmailSender
// ============================================================
// OLD:  Overrode send(). SILENTLY TRUNCATED body to 40 chars:
//       if (body.length() > 40) body = body.substring(0, 40);
//       That's an LSP violation — parent never said "I might cut your data."
//       Caller sends "Hello and welcome to SST!" and gets back a chopped version.
//
// NEW:  Overrides doSend(). Prints FULL body. No silent data loss.
//       Returns SendResult.ok().
// ============================================================

public class EmailSender extends NotificationSender {
    public EmailSender(AuditLog audit) { super(audit); }

    @Override
    protected SendResult doSend(Notification n) {
        System.out.println("EMAIL -> to=" + n.email + " subject=" + n.subject + " body=" + n.body);
        audit.add("email sent");
        return SendResult.ok();
    }
}
