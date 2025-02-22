import java.util.*;

// ============================================================
// NEW CLASS — RoomPricingRegistry
// ============================================================
// WHAT:  A Map that stores room type → price.
//        You register prices from Main, and the calculator just
//        calls getBasePrice() to look them up.
//
// WHY:   OLD code had a big switch inside HostelFeeCalculator:
//          case SINGLE -> base = 14000;
//          case DOUBLE -> base = 15000;
//        Adding a new room type meant EDITING that switch = OCP violation.
//        NOW: just call roomPricing.register(PENTHOUSE, 25000) in Main.
//        Calculator code stays UNTOUCHED.
// ============================================================

public class RoomPricingRegistry {
    private final Map<Integer, Double> prices = new HashMap<>();
    private final double defaultPrice;

    public RoomPricingRegistry(double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public RoomPricingRegistry register(int roomType, double price) {
        prices.put(roomType, price);
        return this;
    }

    public double getBasePrice(int roomType) {
        return prices.getOrDefault(roomType, defaultPrice);
    }
}
