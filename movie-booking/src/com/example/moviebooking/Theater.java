package com.example.moviebooking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Theater {

    private final String id;
    private final String name;
    private final String city;
    private final List<Auditorium> auditoriums;

    public Theater(String id, String name, String city)
    {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(city);
        this.id = id;
        this.name = name;
        this.city = city;
        this.auditoriums = new ArrayList<>();
    }

    public void addAuditorium(Auditorium auditorium)
    {
        auditoriums.add(auditorium);
    }

    

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getCity()
    {
        return city;
    }

    public List<Auditorium> getAuditoriums()
    {
        return Collections.unmodifiableList(auditoriums);
    }

    @Override
    public String toString()
    {
        return name + " [" + city + "] (" + auditoriums.size() + " screens)";
    }
}
