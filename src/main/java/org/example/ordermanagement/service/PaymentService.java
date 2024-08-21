package org.example.ordermanagement.service;

import org.example.ordermanagement.model.Payment;

import java.util.List;

public interface PaymentService {

    Payment createPayment(Payment payment);
    Payment updatePayment(Payment payment);
    Payment getPaymentById(Long id);
    List<Payment> findPaymentsByOrderId(Long orderId);
}
