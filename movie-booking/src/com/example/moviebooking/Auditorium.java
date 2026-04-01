package com.example.moviebooking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Auditorium {
    private final String id;
    private final String name;
    private final List<Seat> seats;

    public Auditorium(String id, String name)
    {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        this.id = id;
        this.name = name;
        this.seats = new ArrayList<>();
    }

    public void addSeat(Seat seat)
    {
        seats.add(seat);
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public List<Seat> getSeats()
    {
        return Collections.unmodifiableList(seats);
    }

    public Seat findSeat(String seatId)
    {
        for (Seat s : seats)
    {
            
            if (s.getId().equals(seatId))
    {
                return s;
            }
        }
        return null;
    }
    @Override
    public String toString()
    {
        return name + " (" + seats.size() + " seats)";
    }
}
