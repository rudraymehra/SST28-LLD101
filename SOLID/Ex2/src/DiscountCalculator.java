// ============================================================
// NEW INTERFACE — DiscountCalculator
// ============================================================
// WHAT:  Contract for discount calculation.
// WHY:   Same reason as TaxCalculator — decouple discount logic.
//        Tomorrow if you want a "FestivalDiscount" or "BulkDiscount",
//        just make a new class that implements this. No changes to
//        CafeteriaSystem needed.
// ============================================================

public interface DiscountCalculator {
    double discountAmount(String customerType, double subtotal, int lineCount);
}
