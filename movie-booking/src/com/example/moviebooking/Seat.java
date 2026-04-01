package com.example.moviebooking;

public class Seat {
    private final String id;
    private final String row;
    private final int number;
    private final SeatType type;

    private final double basePrice;

    public Seat(String id, String row, int number, SeatType type, double basePrice)
    {
        this.id = id;
        this.row = row;
        this.number = number;
        this.type = type;

        this.basePrice = basePrice;

    }
    
     public String getRow()
    {
        return row;
    }

    public String getId()
    {
        return id;
    }

    public int getNumber()
    {
        return number;
    }

     public double getBasePrice()
    {
        return basePrice;
    }
    
    public SeatType getType()
    {
        return type;
    }

   

    @Override
    public String toString()
    {
        return row + number + " (" + type + ")";
    }
}
