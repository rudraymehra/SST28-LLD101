package com.example.moviebooking;

public interface PaymentService {
    Payment pay(double amount);
    Payment refund(double amount);
}
