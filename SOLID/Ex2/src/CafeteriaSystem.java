import java.util.*;

public class CafeteriaSystem {
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();

    // =================================================================
    //  OLD CODE — everything was hard-coded inside this class
    // =================================================================
    // private final FileStore store = new FileStore();       ← concrete class, tight coupling
    // private int invoiceSeq = 1000;
    //
    // public void checkout(String customerType, List<OrderLine> lines) {
    //     String invId = "INV-" + (++invoiceSeq);
    //     StringBuilder out = new StringBuilder();                      ← formatting HERE
    //     out.append("Invoice# ").append(invId).append("\n");           ← formatting HERE
    //
    //     double subtotal = 0.0;
    //     for (OrderLine l : lines) {
    //         MenuItem item = menu.get(l.itemId);
    //         double lineTotal = item.price * l.qty;
    //         subtotal += lineTotal;
    //         out.append(String.format("- %s x%d = %.2f\n", ...));     ← formatting HERE
    //     }
    //
    //     double taxPct = TaxRules.taxPercent(customerType);            ← calling static directly
    //     double tax = subtotal * (taxPct / 100.0);
    //     double discount = DiscountRules.discountAmount(...);          ← calling static directly
    //     double total = subtotal + tax - discount;
    //
    //     out.append(String.format("Subtotal: %.2f\n", subtotal));     ← formatting HERE
    //     out.append(String.format("Tax(%.0f%%): %.2f\n", ...));       ← formatting HERE
    //     out.append(String.format("Discount: -%.2f\n", discount));    ← formatting HERE
    //     out.append(String.format("TOTAL: %.2f\n", total));           ← formatting HERE
    //
    //     String printable = InvoiceFormatter.identityFormat(...);      ← useless wrapper
    //     System.out.print(printable);
    //     store.save(invId, printable);                                 ← direct FileStore usage
    //     System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
    // }
    //
    // PROBLEMS: formatting + tax calc + discount calc + saving = all in ONE method
    // =================================================================


    // =================================================================
    //  NEW CODE — CafeteriaSystem is now a thin ORCHESTRATOR
    // =================================================================

    // 4 dependencies injected — each does ONE job
    private final TaxCalculator taxCalc;           // Job: calculate tax %
    private final DiscountCalculator discountCalc;  // Job: calculate discount amount
    private final InvoiceFormatter formatter;       // Job: format invoice string
    private final InvoiceStore store;               // Job: save invoice (interface!)
    private int invoiceSeq = 1000;

    public CafeteriaSystem(TaxCalculator taxCalc, DiscountCalculator discountCalc,
                           InvoiceFormatter formatter, InvoiceStore store) {
        this.taxCalc = taxCalc;
        this.discountCalc = discountCalc;
        this.formatter = formatter;
        this.store = store;
    }

    public void addToMenu(MenuItem i) { menu.put(i.id, i); }

    // LOOK HOW CLEAN — just calculates numbers and delegates everything else
    public void checkout(String customerType, List<OrderLine> lines) {
        String invId = "INV-" + (++invoiceSeq);

        // Calculate subtotal (simple math, stays here)
        double subtotal = 0.0;
        for (OrderLine l : lines) {
            subtotal += menu.get(l.itemId).price * l.qty;
        }

        // Delegate tax to taxCalc (interface)
        double taxPct = taxCalc.taxPercent(customerType);
        double tax = subtotal * (taxPct / 100.0);

        // Delegate discount to discountCalc (interface)
        double discount = discountCalc.discountAmount(customerType, subtotal, lines.size());

        double total = subtotal + tax - discount;

        // Delegate formatting to formatter (no more StringBuilder here!)
        String printable = formatter.format(invId, lines, menu, subtotal, taxPct, tax, discount, total);
        System.out.print(printable);

        // Delegate saving to store (interface, not FileStore directly!)
        store.save(invId, printable);
        System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
    }
}
