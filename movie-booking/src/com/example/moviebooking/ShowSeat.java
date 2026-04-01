package com.example.moviebooking;

public class ShowSeat {
    private final Seat seat;
    private final double price;
    private boolean available;

    public ShowSeat(Seat seat, double price)
    {
        this.seat = seat;
        this.price = price;
        this.available = true;
    }

    public Seat getSeat()
    {
        return seat;
    }

    public double getPrice()
    {
        return price;
    }

    public boolean isAvailable()
    {
        return available;
    }

    public void book()
    {
        this.available = false;
    }

    public void release()
    {
        this.available = true;
    }

    @Override
    public String toString()
    {
        String status = available ? "AVAILABLE" : "BOOKED";
        return seat + " Rs." + price + " [" + status + "]";
    }
}
