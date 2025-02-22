import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Cafeteria Billing ===");

        // ===== OLD: CafeteriaSystem created everything internally =====
        // CafeteriaSystem sys = new CafeteriaSystem();
        //   ↑ Inside it had: private final FileStore store = new FileStore();
        //   And it called TaxRules.taxPercent() and DiscountRules.discountAmount() directly.

        // ===== NEW: We create each helper and INJECT them =====
        TaxCalculator taxCalc         = new TaxRules();        // TaxRules implements TaxCalculator
        DiscountCalculator discountCalc = new DiscountRules();  // DiscountRules implements DiscountCalculator
        InvoiceFormatter formatter    = new InvoiceFormatter();
        InvoiceStore store            = new FileStore();        // FileStore implements InvoiceStore

        CafeteriaSystem sys = new CafeteriaSystem(taxCalc, discountCalc, formatter, store);

        sys.addToMenu(new MenuItem("M1", "Veg Thali", 80.00));
        sys.addToMenu(new MenuItem("C1", "Coffee", 30.00));
        sys.addToMenu(new MenuItem("S1", "Sandwich", 60.00));

        List<OrderLine> order = List.of(
                new OrderLine("M1", 2),
                new OrderLine("C1", 1)
        );

        sys.checkout("student", order);
    }
}
