package com.example.moviebooking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Show {
    private final String id;
    private final Movie movie;
    private final Auditorium auditorium;
    private final Theater theater;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Map<String, ShowSeat> showSeats;

    public Show(String id, Movie movie, Auditorium auditorium, Theater theater,
                LocalDateTime startTime, Map<SeatType, Double> priceMultipliers)
    {
        Objects.requireNonNull(id);
        
        Objects.requireNonNull(movie);
        Objects.requireNonNull(auditorium);
        Objects.requireNonNull(theater);
        Objects.requireNonNull(startTime);
        this.id = id;
        this.movie = movie;
        this.auditorium = auditorium;
        this.theater = theater;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(movie.getDurationMinutes());
        this.showSeats = new HashMap<>();

        for (Seat seat : auditorium.getSeats())
    {
            double multiplier = priceMultipliers.getOrDefault(seat.getType(), 1.0);
            double finalPrice = seat.getBasePrice() * multiplier;
            showSeats.put(seat.getId(), new ShowSeat(seat, finalPrice));
        }
    }

    public String getId()
    {
        return id;
    }

    public Movie getMovie()
    {
        return movie;
    }

    public Auditorium getAuditorium()
    {
        return auditorium;
    }

    public Theater getTheater()
    {
        return theater;
    }

    public LocalDateTime getStartTime()
    {
        return startTime;
    }

    public LocalDateTime getEndTime()
    {
        return endTime;
    }

    public ShowSeat getShowSeat(String seatId)
    {
        return showSeats.get(seatId);
    }

    public List<ShowSeat> getAvailableSeats()
    {
        List<ShowSeat> available = new ArrayList<>();
        for (ShowSeat ss : showSeats.values())
    {
            if (ss.isAvailable())
    {
                available.add(ss);
            }
        }
        return available;
    }

    public List<ShowSeat> getAllShowSeats()
    {
        return Collections.unmodifiableList(new ArrayList<>(showSeats.values()));
    }

    @Override
    public String toString()
    {
        return movie.getTitle() + " at " + theater.getName() + "/" + auditorium.getName()
                + " [" + startTime + " - " + endTime + "]";
    }
}
