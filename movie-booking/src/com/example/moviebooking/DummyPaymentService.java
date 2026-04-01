package com.example.moviebooking;

import java.util.concurrent.atomic.AtomicInteger;

public class DummyPaymentService implements PaymentService {
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Payment pay(double amount)
    {
        String payId = "PAY-" + counter.incrementAndGet();
        return new Payment(payId, amount, PaymentStatus.SUCCESS);
    }

    @Override
    public Payment refund(double amount)
    {
        String refundId = "REF-" + counter.incrementAndGet();
        return new Payment(refundId, amount, PaymentStatus.SUCCESS);
    }
}
