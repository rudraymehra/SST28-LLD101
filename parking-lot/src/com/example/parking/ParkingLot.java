package com.example.parking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


public class ParkingLot {
    private final List<ParkingSlot> slots;
    private final Map<Integer, Gate> gates;
    private final SlotAssignmentStrategy assignmentStrategy;
    private final BillingService billingService;
    private final Map<String, ParkingTicket> activeTickets;
    private int ticketCounter;

    public ParkingLot(List<ParkingSlot> slots, List<Gate> gates, SlotAssignmentStrategy assignmentStrategy,  BillingService billingService)
    {
        this.slots              = new ArrayList<>(Objects.requireNonNull(slots));
        this.assignmentStrategy = Objects.requireNonNull(assignmentStrategy);
        this.billingService     = Objects.requireNonNull(billingService);
        this.activeTickets      = new HashMap<>();
        this.ticketCounter      = 0;

        this.gates = new HashMap<>();
        for (Gate g : gates)
    {
            this.gates.put(g.getGateNumber(), g);
        }
    }

    public ParkingTicket park(Vehicle vehicle, LocalDateTime entryTime, SlotType requestedSlotType, int entryGateId)
    {

        Gate entryGate = gates.get(entryGateId);
        if (entryGate == null)
    {
            throw new IllegalArgumentException("Unknown gate ID: " + entryGateId);
        }

        Optional<ParkingSlot> found = assignmentStrategy.findSlot( slots, vehicle.getType(), requestedSlotType, entryGate);

        if (found.isEmpty())
    {
            throw new IllegalStateException("No available slot for " + vehicle);
        }

        ParkingSlot slot = found.get();
        slot.occupy();

        String id = "TKT-" +(++ticketCounter);
        ParkingTicket ticket = new ParkingTicket(id, vehicle, slot, entryTime);
        activeTickets.put(id,ticket);
        return ticket;
    }

    public Map<SlotType, long[]> status()
    {
        Map<SlotType, long[]> result = new LinkedHashMap<>();

        for (SlotType type : SlotType.values())
    {
            long total     = slots.stream().filter(s -> s.getType() == type).count();
            long available = slots.stream().filter(s -> s.getType() == type && !s.isOccupied()).count();
            long occupied  = total - available;
            result.put(type, new long[]{ total, available, occupied });
        }

        return result;
    }

    public Bill exit(ParkingTicket ticket, LocalDateTime exitTime)
    {
        ParkingTicket removed = activeTickets.remove(ticket.getTicketId());
        
        if (removed == null)
    {
            throw new IllegalArgumentException("No active ticket found: " + ticket.getTicketId());
        }

        removed.getSlot().free();
        return billingService.generateBill(removed, exitTime);
    }
}
