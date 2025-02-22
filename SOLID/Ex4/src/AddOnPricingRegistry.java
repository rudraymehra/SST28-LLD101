import java.util.*;

// ============================================================
// NEW CLASS — AddOnPricingRegistry
// ============================================================
// WHAT:  Same idea as RoomPricingRegistry but for add-ons.
//
// WHY:   OLD code had:
//          if (a == MESS) add += 1000;
//          else if (a == LAUNDRY) add += 500;
//        Adding GYM_PLUS? Edit that chain. OCP violation.
//        NOW: addOnPricing.register(AddOn.GYM_PLUS, 600) in Main. Done.
// ============================================================

public class AddOnPricingRegistry {
    private final Map<AddOn, Double> prices = new HashMap<>();

    public AddOnPricingRegistry register(AddOn addOn, double price) {
        prices.put(addOn, price);
        return this;
    }

    public double getPrice(AddOn addOn) {
        return prices.getOrDefault(addOn, 0.0);
    }
}
