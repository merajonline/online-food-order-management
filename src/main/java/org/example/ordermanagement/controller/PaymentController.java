package org.example.ordermanagement.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ordermanagement.handler.PaymentHandler;
import org.example.ordermanagement.model.ApiConverter;
import org.example.ordermanagement.model.Payment;
import org.example.ordermanagement.model.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/api/order/payment")
public class PaymentController {

    private final PaymentHandler paymentHandler;

    @PostMapping(value = "/create")
    public Result<Payment> createPayment(@Validated @RequestBody Payment payment) {
        log.info("into createPayment");
        return ApiConverter.toResult(paymentHandler.createPayment(payment));
    }

    @GetMapping(value = "/get/{id}")
    public Result<Payment> getPayment(@Validated @PathVariable(value = "id") Long id) {
        log.info("into getPayment");
        return ApiConverter.toResult(paymentHandler.getPaymentById(id));
    }

    @PatchMapping(value = "/update")
    public Result<Payment> updatePayment(@Validated @RequestBody Payment payment) {
        log.info("into updateOrder");
        return ApiConverter.toResult(paymentHandler.updatePayment(payment));
    }
}
