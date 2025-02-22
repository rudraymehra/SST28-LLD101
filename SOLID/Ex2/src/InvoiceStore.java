// ============================================================
// NEW INTERFACE — InvoiceStore
// ============================================================
// WHAT:  Contract for saving and reading invoices.
// WHY:   CafeteriaSystem was directly using FileStore (concrete class).
//        Now it talks to this interface. Tomorrow you can make
//        DatabaseStore, CloudStore, etc. — CafeteriaSystem won't know
//        or care.
// ============================================================

public interface InvoiceStore {
    void save(String id, String content);
    int countLines(String id);
}
