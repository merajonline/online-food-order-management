package org.example.ordermanagement.handler.impl;

import lombok.RequiredArgsConstructor;
import org.example.ordermanagement.handler.PaymentHandler;
import org.example.ordermanagement.model.Payment;
import org.example.ordermanagement.service.PaymentService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentHandlerImpl implements PaymentHandler {

    private final PaymentService paymentService;
    @Override
    public Payment createPayment(Payment payment) {
        return paymentService.createPayment(payment);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        return paymentService.updatePayment(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentService.getPaymentById(id);
    }
}
