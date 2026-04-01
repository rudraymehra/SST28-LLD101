package com.example.moviebooking;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class Ticket {
    private final String id;
    private final Show show;
    private final List<ShowSeat> bookedSeats;
    private final double totalAmount;
    private final LocalDateTime bookingTime;
    private TicketStatus status;

    public Ticket(String id, Show show, List<ShowSeat> bookedSeats, double totalAmount, LocalDateTime bookingTime)
    {
        this.id = id;
        this.show = show;
        this.bookedSeats = bookedSeats;
        this.totalAmount = totalAmount;
        this.bookingTime = bookingTime;
        this.status = TicketStatus.CONFIRMED;
    }

    public String getId()
    {
        return id;
    }

    public Show getShow()
    {
        return show;
    }

    public List<ShowSeat> getBookedSeats()
    {
        return Collections.unmodifiableList(bookedSeats);
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public LocalDateTime getBookingTime()
    {
        return bookingTime;
    }

    public TicketStatus getStatus()
    {
        return status;
    }

    public void cancel()
    {
        this.status = TicketStatus.CANCELLED;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Ticket[").append(id).append("] ").append(status).append("\n");
        sb.append("  Show: ").append(show).append("\n");
        sb.append("  Seats: ");
        for (ShowSeat ss : bookedSeats)
    {
            sb.append(ss.getSeat()).append(" ");
        }
        sb.append("\n  Total: Rs.").append(totalAmount);
        return sb.toString();
    }
}
