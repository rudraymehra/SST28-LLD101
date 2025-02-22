import java.util.*;

// ============================================================
// MODIFIED — FileStore
// ============================================================
// WHAT CHANGED: Added "implements InvoiceStore"
// OLD:  public class FileStore {
// NEW:  public class FileStore implements InvoiceStore {
//
// Inside code is EXACTLY the same. Just follows the interface now.
// ============================================================

public class FileStore implements InvoiceStore {
    private final Map<String, String> files = new HashMap<>();

    public void save(String name, String content) {
        files.put(name, content);
    }

    public int countLines(String name) {
        String c = files.getOrDefault(name, "");
        if (c.isEmpty()) return 0;
        return c.split("\n").length;
    }
}
