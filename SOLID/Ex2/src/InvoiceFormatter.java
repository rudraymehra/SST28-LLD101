import java.util.*;

// ============================================================
// MODIFIED — InvoiceFormatter
// ============================================================
// OLD:  Was a useless one-liner:
//       public static String identityFormat(String s) { return s; }
//       It did NOTHING — just returned the same string.
//       The actual formatting was happening inside CafeteriaSystem.checkout()
//
// NEW:  Now it ACTUALLY does the formatting job.
//       All the StringBuilder logic that was inside checkout() is now HERE.
//
// WHY:  Formatting an invoice is NOT CafeteriaSystem's job.
//       CafeteriaSystem should just calculate numbers and pass them
//       to the formatter. Formatter decides HOW it looks on screen.
// ============================================================

public class InvoiceFormatter {

    public String format(String invId, List<OrderLine> lines, Map<String, MenuItem> menu,
                         double subtotal, double taxPct, double tax,
                         double discount, double total) {
        StringBuilder out = new StringBuilder();
        out.append("Invoice# ").append(invId).append("\n");

        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            double lineTotal = item.price * l.qty;
            out.append(String.format("- %s x%d = %.2f\n", item.name, l.qty, lineTotal));
        }

        out.append(String.format("Subtotal: %.2f\n", subtotal));
        out.append(String.format("Tax(%.0f%%): %.2f\n", taxPct, tax));
        out.append(String.format("Discount: -%.2f\n", discount));
        out.append(String.format("TOTAL: %.2f\n", total));
        return out.toString();
    }
}
