import java.util.*;

public class HostelFeeCalculator {

    // =================================================================
    //  OLD CODE — switch + if/else chains (OCP violation)
    // =================================================================
    // private final FakeBookingRepo repo;
    //
    // public HostelFeeCalculator(FakeBookingRepo repo) { this.repo = repo; }
    //
    // private Money calculateMonthly(BookingRequest req) {
    //     double base;
    //     switch (req.roomType) {                        ← hard-coded room prices
    //         case LegacyRoomTypes.SINGLE -> base = 14000.0;
    //         case LegacyRoomTypes.DOUBLE -> base = 15000.0;
    //         case LegacyRoomTypes.TRIPLE -> base = 12000.0;
    //         default -> base = 16000.0;
    //     }
    //
    //     double add = 0.0;
    //     for (AddOn a : req.addOns) {                   ← hard-coded add-on prices
    //         if (a == AddOn.MESS) add += 1000.0;
    //         else if (a == AddOn.LAUNDRY) add += 500.0;
    //         else if (a == AddOn.GYM) add += 300.0;
    //     }
    //
    //     return new Money(base + add);
    // }
    //
    // PROBLEM: New room type or add-on = edit this method = OCP violation.
    // =================================================================


    // =================================================================
    //  NEW CODE — registries replace switch/if-else
    // =================================================================

    private final RoomPricingRegistry roomPricing;     // looks up room prices
    private final AddOnPricingRegistry addOnPricing;   // looks up add-on prices
    private final FakeBookingRepo repo;

    public HostelFeeCalculator(RoomPricingRegistry roomPricing, AddOnPricingRegistry addOnPricing,
                               FakeBookingRepo repo) {
        this.roomPricing = roomPricing;
        this.addOnPricing = addOnPricing;
        this.repo = repo;
    }

    public void process(BookingRequest req) {
        Money monthly = calculateMonthly(req);
        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000));
        repo.save(bookingId, req, monthly, deposit);
    }

    // LOOK — no switch, no if/else. Just registry lookups!
    private Money calculateMonthly(BookingRequest req) {
        double base = roomPricing.getBasePrice(req.roomType);
        double add = 0.0;
        for (AddOn a : req.addOns) {
            add += addOnPricing.getPrice(a);
        }
        return new Money(base + add);
    }
}
