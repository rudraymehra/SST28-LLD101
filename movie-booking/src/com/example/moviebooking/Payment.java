package com.example.moviebooking;

public class Payment {
    private final String id;
    private final double amount;
    private final PaymentStatus status;

    public Payment(String id, double amount, PaymentStatus status)
    {
        this.id = id;
        this.amount = amount;
        this.status = status;
    }

    public String getId()
    {
        return id;
    }

    public double getAmount()
    {
        return amount;
    }

    public PaymentStatus getStatus()
    {
        return status;
    }

    @Override
    public String toString()
    {
        return "Payment[" + id + "] Rs." + amount + " " + status;
    }
}
