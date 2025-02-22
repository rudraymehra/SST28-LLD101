// ============================================================
// NEW INTERFACE — TaxCalculator
// ============================================================
// WHAT:  A contract that says "whoever implements me must provide
//        a taxPercent() method"
// WHY:   CafeteriaSystem was calling TaxRules.taxPercent() DIRECTLY.
//        That's tight coupling. Now CafeteriaSystem talks to this
//        INTERFACE. It doesn't care if it's TaxRules, or
//        GSTCalculator, or FlatTaxCalculator behind the scenes.
// BENEFIT: Swap tax logic without touching CafeteriaSystem.
// ============================================================

public interface TaxCalculator {
    double taxPercent(String customerType);
}
