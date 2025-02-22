public class Main {
    public static void main(String[] args) {
        System.out.println("=== Notification Demo ===");
        AuditLog audit = new AuditLog();

        Notification n = new Notification("Welcome", "Hello and welcome to SST!", "riya@sst.edu", "9876543210");

        NotificationSender email = new EmailSender(audit);
        NotificationSender sms = new SmsSender(audit);
        NotificationSender wa = new WhatsAppSender(audit);

        email.send(n);
        sms.send(n);

        // ===== OLD: needed try/catch because WhatsApp THREW exceptions =====
        // try {
        //     wa.send(n);
        // } catch (RuntimeException ex) {
        //     System.out.println("WA ERROR: " + ex.getMessage());
        //     audit.add("WA failed");
        // }

        // ===== NEW: no try/catch! Just check the result. =====
        SendResult waResult = wa.send(n);
        if (!waResult.success) {
            System.out.println("WA ERROR: " + waResult.error);
            audit.add("WA failed");
        }

        System.out.println("AUDIT entries=" + audit.size());
    }
}
