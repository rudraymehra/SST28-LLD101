package com.example.moviebooking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingSystem {
    private final Map<String, Movie> movies;
    private final Map<String, Theater> theaters;
    private final Map<String, Show> shows;
    private final Map<String, Ticket> tickets;

    public BookingSystem()
    {
        this.movies = new HashMap<>();
        this.theaters = new HashMap<>();
        this.shows = new HashMap<>();
        this.tickets = new HashMap<>();
    }

    public void addMovie(Movie movie)
    {
        if (movies.containsKey(movie.getId()))
    {
            throw new IllegalArgumentException("movie already exists: " + movie.getId());
        }
        movies.put(movie.getId(), movie);
    }

    public void addTheater(Theater theater)
    {
        if (theaters.containsKey(theater.getId()))
    {
            throw new IllegalArgumentException("theater already exists: " + theater.getId());
        }
        theaters.put(theater.getId(), theater);
    }

    public void addShow(Show show)
    {
        if (shows.containsKey(show.getId()))
    {
            throw new IllegalArgumentException("show already exists: " + show.getId());
        }
        shows.put(show.getId(), show);
    }

    public void addTicket(Ticket ticket)
    {
        tickets.put(ticket.getId(), ticket);
    }

    public Movie getMovie(String id)
    {
        return movies.get(id);
    }

    public Theater getTheater(String id)
    {
        return theaters.get(id);
    }

    public Show getShow(String id)
    {
        return shows.get(id);
    }

    public Ticket getTicket(String id)
    {
        return tickets.get(id);
    }

    public List<Theater> getTheatersByCity(String city)
    {
        List<Theater> result = new ArrayList<>();
        for (Theater t : theaters.values())
    {
            if (t.getCity().equalsIgnoreCase(city))
    {
                result.add(t);
            }
        }
        return result;
    }

    public List<Movie> getMoviesByCity(String city)
    {
        List<Movie> result = new ArrayList<>();
        List<String> seen = new ArrayList<>();
        for (Show show : shows.values())
    {
            if (show.getTheater().getCity().equalsIgnoreCase(city) && !seen.contains(show.getMovie().getId()))
    {
                result.add(show.getMovie());
                seen.add(show.getMovie().getId());
            }
        }
        return result;
    }

    public List<Show> getShowsByMovie(String movieId, String city)
    {
        List<Show> result = new ArrayList<>();
        for (Show show : shows.values())
    {
            if (show.getMovie().getId().equals(movieId)
                    && show.getTheater().getCity().equalsIgnoreCase(city))
    {
                result.add(show);
            }
        }
        return result;
    }

    public List<Show> getShowsByTheater(String theaterId)
    {
        List<Show> result = new ArrayList<>();
        for (Show show : shows.values())
    {
            if (show.getTheater().getId().equals(theaterId))
    {
                result.add(show);
            }
        }
        return result;
    }
}
