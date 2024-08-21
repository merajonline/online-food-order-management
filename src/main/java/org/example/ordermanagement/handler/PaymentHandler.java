package org.example.ordermanagement.handler;

import org.example.ordermanagement.model.Payment;

public interface PaymentHandler {

    Payment createPayment(Payment payment);
    Payment updatePayment(Payment payment);
    Payment getPaymentById(Long id);

}
