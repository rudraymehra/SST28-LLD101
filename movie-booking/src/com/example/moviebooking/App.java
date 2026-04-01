package com.example.moviebooking;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args)
    {
        BookingSystem bookingSystem = new BookingSystem();
        AdminService admin = new AdminService(bookingSystem);
        CustomerService customer = new CustomerService(bookingSystem, new DummyPaymentService());

        System.out.println("=== Admin: Setting up movies ===");
        Movie m1 = admin.addMovie("M1", "Inception", "Sci-Fi", 148);
        Movie m2 = admin.addMovie("M2", "The Dark Knight", "Action", 152);
        System.out.println("  Added: " + m1);
        System.out.println("  Added: " + m2);

        System.out.println();
        System.out.println("=== Admin: Setting up theaters ===");
        Theater t1 = admin.addTheater("T1", "PVR Phoenix", "Mumbai");
        Theater t2 = admin.addTheater("T2", "INOX Metro", "Mumbai");
        Theater t3 = admin.addTheater("T3", "PVR Select", "Delhi");

        Auditorium a1 = new Auditorium("A1", "Screen 1");
        a1.addSeat(new Seat("S1", "A", 1, SeatType.SILVER, 150));
        a1.addSeat(new Seat("S2", "A", 2, SeatType.SILVER, 150));
        a1.addSeat(new Seat("S3", "B", 1, SeatType.GOLD, 250));
        a1.addSeat(new Seat("S4", "B", 2, SeatType.GOLD, 250));
        a1.addSeat(new Seat("S5", "C", 1, SeatType.PLATINUM, 400));
        a1.addSeat(new Seat("S6", "C", 2, SeatType.PLATINUM, 400));
        t1.addAuditorium(a1);

        Auditorium a2 = new Auditorium("A2", "Screen 2");
        a2.addSeat(new Seat("S7", "A", 1, SeatType.SILVER, 120));
        a2.addSeat(new Seat("S8", "A", 2, SeatType.SILVER, 120));
        a2.addSeat(new Seat("S9", "B", 1, SeatType.GOLD, 200));
        a2.addSeat(new Seat("S10", "B", 2, SeatType.GOLD, 200));
        t1.addAuditorium(a2);

        Auditorium a3 = new Auditorium("A3", "Audi 1");
        a3.addSeat(new Seat("S11", "A", 1, SeatType.SILVER, 140));
        a3.addSeat(new Seat("S12", "A", 2, SeatType.SILVER, 140));
        a3.addSeat(new Seat("S13", "B", 1, SeatType.GOLD, 220));
        a3.addSeat(new Seat("S14", "B", 2, SeatType.GOLD, 220));
        t2.addAuditorium(a3);

        Auditorium a4 = new Auditorium("A4", "Gold Screen");
        a4.addSeat(new Seat("S15", "A", 1, SeatType.GOLD, 300));
        a4.addSeat(new Seat("S16", "A", 2, SeatType.GOLD, 300));
        a4.addSeat(new Seat("S17", "B", 1, SeatType.PLATINUM, 500));
        a4.addSeat(new Seat("S18", "B", 2, SeatType.PLATINUM, 500));
        t3.addAuditorium(a4);

        System.out.println("  " + t1);
        System.out.println("  " + t2);
        System.out.println("  " + t3);

        System.out.println();
        System.out.println("=== Admin: Adding shows ===");
        Map<SeatType, Double> normalPricing = new HashMap<>();
        normalPricing.put(SeatType.SILVER, 1.0);
        normalPricing.put(SeatType.GOLD, 1.0);
        normalPricing.put(SeatType.PLATINUM, 1.0);

        Map<SeatType, Double> weekendPricing = new HashMap<>();
        weekendPricing.put(SeatType.SILVER, 1.5);
        weekendPricing.put(SeatType.GOLD, 1.5);
        weekendPricing.put(SeatType.PLATINUM, 2.0);

        Map<SeatType, Double> premierePricing = new HashMap<>();
        premierePricing.put(SeatType.SILVER, 2.0);
        premierePricing.put(SeatType.GOLD, 2.5);
        premierePricing.put(SeatType.PLATINUM, 3.0);

        Show show1 = admin.addMovieShow("M1", "T1", "A1",
                LocalDateTime.of(2026, 4, 1, 10, 0), normalPricing);
        Show show2 = admin.addMovieShow("M1", "T1", "A2",
                LocalDateTime.of(2026, 4, 1, 14, 0), weekendPricing);
        Show show3 = admin.addMovieShow("M2", "T2", "A3",
                LocalDateTime.of(2026, 4, 1, 18, 0), premierePricing);
        Show show4 = admin.addMovieShow("M2", "T3", "A4",
                LocalDateTime.of(2026, 4, 1, 20, 0), weekendPricing);

        System.out.println("  " + show1);
        System.out.println("  " + show2);
        System.out.println("  " + show3);
        System.out.println("  " + show4);

        System.out.println();
        System.out.println("=== Customer: Browse theaters in Mumbai ===");
        List<Theater> mumbaiTheaters = customer.showTheaters("Mumbai");
        for (Theater t : mumbaiTheaters)
    {
            System.out.println("  " + t);
        }

        System.out.println();
        System.out.println("=== Customer: Browse movies in Mumbai ===");
        List<Movie> mumbaiMovies = customer.showMovies("Mumbai");
        for (Movie m : mumbaiMovies)
    {
            System.out.println("  " + m);
        }

        System.out.println();
        System.out.println("=== Customer: Shows for Inception in Mumbai ===");
        List<Show> inceptionShows = customer.showsByMovie("M1", "Mumbai");
        for (Show s : inceptionShows)
    {
            System.out.println("  " + s);
        }

        System.out.println();
        System.out.println("=== Customer: View available seats for Show 1 (normal pricing) ===");
        for (ShowSeat ss : show1.getAvailableSeats())
    {
            System.out.println("  " + ss);
        }

        System.out.println();
        System.out.println("=== Customer: Book 2 seats (Silver A1, Gold B1) for Show 1 ===");
        Ticket ticket1 = customer.bookTicket(show1.getId(), Arrays.asList("S1", "S3"));
        System.out.println(ticket1);

        System.out.println();
        System.out.println("=== Customer: View available seats after booking ===");
        for (ShowSeat ss : show1.getAvailableSeats())
    {
            System.out.println("  " + ss);
        }

        System.out.println();
        System.out.println("=== Customer: Book Platinum seats for Show 1 ===");
        Ticket ticket2 = customer.bookTicket(show1.getId(), Arrays.asList("S5", "S6"));
        System.out.println(ticket2);

        System.out.println();
        System.out.println("=== Customer: Try booking already booked seat S1 ===");
        try {
            customer.bookTicket(show1.getId(), Arrays.asList("S1"));
        } catch (IllegalStateException e)
    {
            System.out.println("  Expected error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Customer: Book seats for Show 2 (weekend pricing) ===");
        Ticket ticket3 = customer.bookTicket(show2.getId(), Arrays.asList("S7", "S9"));
        System.out.println(ticket3);

        System.out.println();
        System.out.println("=== Customer: Book seats for Show 3 (premiere pricing) ===");
        Ticket ticket4 = customer.bookTicket(show3.getId(), Arrays.asList("S11", "S13"));
        System.out.println(ticket4);

        System.out.println();
        System.out.println("=== Pricing comparison across shows ===");
        System.out.println("  Show 1 (normal):   Silver=" + show1.getShowSeat("S1").getPrice()
                + " Gold=" + show1.getShowSeat("S3").getPrice()
                + " Platinum=" + show1.getShowSeat("S5").getPrice());
        System.out.println("  Show 2 (weekend):  Silver=" + show2.getShowSeat("S7").getPrice()
                + " Gold=" + show2.getShowSeat("S9").getPrice());
        System.out.println("  Show 3 (premiere): Silver=" + show3.getShowSeat("S11").getPrice()
                + " Gold=" + show3.getShowSeat("S13").getPrice());

        System.out.println();
        System.out.println("=== Customer: Cancel ticket 1 and get refund ===");
        Payment refund = customer.cancelTicket(ticket1.getId());
        System.out.println("  Ticket status: " + ticket1.getStatus());
        System.out.println("  Refund: " + refund);

        System.out.println();
        System.out.println("=== Customer: Verify seats released after cancellation ===");
        ShowSeat s1After = show1.getShowSeat("S1");
        ShowSeat s3After = show1.getShowSeat("S3");
        System.out.println("  S1 available: " + s1After.isAvailable());
        System.out.println("  S3 available: " + s3After.isAvailable());

        System.out.println();
        System.out.println("=== Customer: Re-book the released seats ===");
        Ticket ticket5 = customer.bookTicket(show1.getId(), Arrays.asList("S1", "S3"));
        System.out.println(ticket5);

        System.out.println();
        System.out.println("=== Customer: Try cancelling already cancelled ticket ===");
        try {
            customer.cancelTicket(ticket1.getId());
        } catch (IllegalStateException e)
    {
            System.out.println("  Expected error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Payment failure scenario ===");
        CustomerService failCustomer = new CustomerService(bookingSystem, new FailingPaymentService());
        try {
            failCustomer.bookTicket(show1.getId(), Arrays.asList("S2"));
        } catch (RuntimeException e)
    {
            System.out.println("  Expected error: " + e.getMessage());
        }
        System.out.println("  S2 still available: " + show1.getShowSeat("S2").isAvailable());

        System.out.println();
        System.out.println("=== Customer: Browse movies in Delhi ===");
        List<Movie> delhiMovies = customer.showMovies("Delhi");
        for (Movie m : delhiMovies)
    {
            System.out.println("  " + m);
        }

        System.out.println();
        System.out.println("=== Customer: Book seats in Delhi (Show 4, weekend pricing) ===");
        Ticket ticket6 = customer.bookTicket(show4.getId(), Arrays.asList("S15", "S17"));
        System.out.println(ticket6);

        System.out.println();
        System.out.println("=== Admin: Try adding duplicate movie ===");
        try {
            admin.addMovie("M1", "Inception Duplicate", "Sci-Fi", 148);
        } catch (IllegalArgumentException e)
    {
            System.out.println("  Expected error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Admin: Try adding show with invalid movie ===");
        try {
            admin.addMovieShow("M99", "T1", "A1",
                    LocalDateTime.of(2026, 4, 2, 10, 0), normalPricing);
        } catch (IllegalArgumentException e)
    {
            System.out.println("  Expected error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Admin: Try adding show with invalid auditorium ===");
        try {
            admin.addMovieShow("M1", "T1", "A99",
                    LocalDateTime.of(2026, 4, 2, 10, 0), normalPricing);
        } catch (IllegalArgumentException e)
    {
            System.out.println("  Expected error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Customer: Try booking with no seats ===");
        try {
            customer.bookTicket(show1.getId(), Arrays.asList());
        } catch (IllegalArgumentException e)
    {
            System.out.println("  Expected error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Customer: Try booking invalid show ===");
        try {
            customer.bookTicket("SHOW-999", Arrays.asList("S1"));
        } catch (IllegalArgumentException e)
    {
            System.out.println("  Expected error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Customer: Try booking invalid seat in valid show ===");
        try {
            customer.bookTicket(show1.getId(), Arrays.asList("S99"));
        } catch (IllegalArgumentException e)
    {
            System.out.println("  Expected error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Customer: Try cancelling invalid ticket ===");
        try {
            customer.cancelTicket("TKT-999");
        } catch (IllegalArgumentException e)
    {
            System.out.println("  Expected error: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Multiple auditoriums in same theater (different shows) ===");
        List<Show> pvrShows = bookingSystem.getShowsByTheater("T1");
        System.out.println("  PVR Phoenix has " + pvrShows.size() + " shows:");
        for (Show s : pvrShows)
    {
            System.out.println("    " + s);
        }

        System.out.println();
        System.out.println("=== Demo Complete ===");
    }
}
