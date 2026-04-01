package com.example.moviebooking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerService {
    private final BookingSystem system;
    private final PaymentService paymentService;
    private final AtomicInteger ticketCounter = new AtomicInteger(0);

    public CustomerService(BookingSystem system, PaymentService paymentService)
    {
        Objects.requireNonNull(system);
        Objects.requireNonNull(paymentService);
        this.system = system;
        this.paymentService = paymentService;
    }

    public Ticket bookTicket(String showId, List<String> seatIds)
    {
        Show show = system.getShow(showId);
        if (show == null)
    {
            throw new IllegalArgumentException("show not found: " + showId);
        }
        if (seatIds == null || seatIds.isEmpty())
    {
            throw new IllegalArgumentException("must select at least one seat");
        }

        List<ShowSeat> toBook = new ArrayList<>();
        double totalAmount = 0;

        for (String seatId : seatIds)
    {
            ShowSeat showSeat = show.getShowSeat(seatId);
            if (showSeat == null)
    {
                throw new IllegalArgumentException("seat not found in show: " + seatId);
            }
            if (!showSeat.isAvailable())
    {
                throw new IllegalStateException("seat already booked: " + seatId);
            }
            toBook.add(showSeat);
            totalAmount += showSeat.getPrice();
        }

        Payment payment = paymentService.pay(totalAmount);
        if (payment.getStatus() != PaymentStatus.SUCCESS)
    {
            throw new RuntimeException("payment failed for amount: " + totalAmount);
        }

        for (ShowSeat ss : toBook)
    {
            ss.book();
        }

        String ticketId = "TKT-" + ticketCounter.incrementAndGet();
        Ticket ticket = new Ticket(ticketId, show, toBook, totalAmount, LocalDateTime.now());
        system.addTicket(ticket);
        return ticket;
    }

    public List<Theater> showTheaters(String city)
    {
        return system.getTheatersByCity(city);
    }

    public List<Movie> showMovies(String city)
    {
        return system.getMoviesByCity(city);
    }

    public List<Show> showsByMovie(String movieId, String city)
    {
        return system.getShowsByMovie(movieId, city);
    }

    public Payment cancelTicket(String ticketId)
    {
        Ticket ticket = system.getTicket(ticketId);
        if (ticket == null)
    {
            throw new IllegalArgumentException("ticket not found: " + ticketId);
        }
        if (ticket.getStatus() == TicketStatus.CANCELLED)
    {
            throw new IllegalStateException("ticket already cancelled: " + ticketId);
        }

        ticket.cancel();
        for (ShowSeat ss : ticket.getBookedSeats())
    {
            ss.release();
        }

        Payment refund = paymentService.refund(ticket.getTotalAmount());
        if (refund.getStatus() != PaymentStatus.SUCCESS)
    {
            throw new RuntimeException("refund failed for ticket: " + ticketId);
        }
        return refund;
    }
}
