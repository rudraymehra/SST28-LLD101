// ============================================================
// MODIFIED — TaxRules
// ============================================================
// WHAT CHANGED: Added "implements TaxCalculator"
//               Removed "static" from the method.
// OLD:  public class TaxRules {
//           public static double taxPercent(String customerType) { ... }
// NEW:  public class TaxRules implements TaxCalculator {
//           public double taxPercent(String customerType) { ... }
//
// WHY:  "static" means you call TaxRules.taxPercent() directly.
//       That's hard-coded dependency. By making it an instance method
//       implementing an interface, CafeteriaSystem can receive ANY
//       TaxCalculator — not just TaxRules.
// Inside logic is EXACTLY the same.
// ============================================================

public class TaxRules implements TaxCalculator {
    public double taxPercent(String customerType) {
        if ("student".equalsIgnoreCase(customerType)) return 5.0;
        if ("staff".equalsIgnoreCase(customerType)) return 2.0;
        return 8.0;
    }
}
