// ============================================================
// MODIFIED — SmsSender
// ============================================================
// OLD:  Overrode send(), returned void.
// NEW:  Overrides doSend(), returns SendResult.ok().
//       Logic is the same otherwise.
// ============================================================

public class SmsSender extends NotificationSender {
    public SmsSender(AuditLog audit) { super(audit); }

    @Override
    protected SendResult doSend(Notification n) {
        System.out.println("SMS -> to=" + n.phone + " body=" + n.body);
        audit.add("sms sent");
        return SendResult.ok();
    }
}
