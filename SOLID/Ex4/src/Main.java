import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");

        // ===== OLD: just did new HostelFeeCalculator(new FakeBookingRepo()) =====
        // Prices were hard-coded INSIDE the calculator as switch/if-else.

        // ===== NEW: register prices HERE, calculator just looks them up =====
        // Want to add PENTHOUSE room? Just add: roomPricing.register(5, 25000.0);
        // Calculator code stays UNTOUCHED. That's OCP!
        RoomPricingRegistry roomPricing = new RoomPricingRegistry(16000.0);
        roomPricing.register(LegacyRoomTypes.SINGLE, 14000.0);
        roomPricing.register(LegacyRoomTypes.DOUBLE, 15000.0);
        roomPricing.register(LegacyRoomTypes.TRIPLE, 12000.0);

        AddOnPricingRegistry addOnPricing = new AddOnPricingRegistry();
        addOnPricing.register(AddOn.MESS, 1000.0);
        addOnPricing.register(AddOn.LAUNDRY, 500.0);
        addOnPricing.register(AddOn.GYM, 300.0);

        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS));
        HostelFeeCalculator calc = new HostelFeeCalculator(roomPricing, addOnPricing, new FakeBookingRepo());
        calc.process(req);
    }
}
