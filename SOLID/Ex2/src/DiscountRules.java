// ============================================================
// MODIFIED — DiscountRules
// ============================================================
// WHAT CHANGED: Added "implements DiscountCalculator"
//               Removed "static" from the method.
//               Parameter name changed: distinctLines → lineCount
// OLD:  public class DiscountRules {
//           public static double discountAmount(...) { ... }
// NEW:  public class DiscountRules implements DiscountCalculator {
//           public double discountAmount(...) { ... }
//
// Inside logic is EXACTLY the same.
// ============================================================

public class DiscountRules implements DiscountCalculator {
    public double discountAmount(String customerType, double subtotal, int lineCount) {
        if ("student".equalsIgnoreCase(customerType)) {
            if (subtotal >= 180.0) return 10.0;
            return 0.0;
        }
        if ("staff".equalsIgnoreCase(customerType)) {
            if (lineCount >= 3) return 15.0;
            return 5.0;
        }
        return 0.0;
    }
}
