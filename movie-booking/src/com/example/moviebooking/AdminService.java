package com.example.moviebooking;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class AdminService {
    private final BookingSystem system;
    private final AtomicInteger showCounter = new AtomicInteger(0);

    public AdminService(BookingSystem system)
    {
        Objects.requireNonNull(system);
        this.system = system;
    }

    public Movie addMovie(String id, String title, String genre, int durationMinutes)
    {
        Movie movie = new Movie(id, title, genre, durationMinutes);
        system.addMovie(movie);
        return movie;
    }

    public Theater addTheater(String id, String name, String city)
    {
        Theater theater = new Theater(id, name, city);
        system.addTheater(theater);
        return theater;
    }

    public Show addMovieShow(String movieId, String theaterId, String auditoriumId,
                             LocalDateTime startTime, Map<SeatType, Double> priceMultipliers)
    {
        Movie movie = system.getMovie(movieId);
        if (movie == null)
    {
            throw new IllegalArgumentException("movie not found: " + movieId);
        }
        Theater theater = system.getTheater(theaterId);
        if (theater == null)
    {
            throw new IllegalArgumentException("theater not found: " + theaterId);
        }
        Auditorium auditorium = null;
        for (Auditorium a : theater.getAuditoriums())
    {
            if (a.getId().equals(auditoriumId))
    {
                auditorium = a;
                break;
            }
        }
        if (auditorium == null)
    {
            throw new IllegalArgumentException("auditorium not found: " + auditoriumId);
        }

        String showId = "SHOW-" + showCounter.incrementAndGet();
        Show show = new Show(showId, movie, auditorium, theater, startTime, priceMultipliers);
        system.addShow(show);
        return show;
    }
}
