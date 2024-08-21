package org.example.ordermanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.ordermanagement.exception.BixException;
import org.example.ordermanagement.model.Payment;
import org.example.ordermanagement.repository.PaymentRepository;
import org.example.ordermanagement.service.PaymentService;
import org.example.ordermanagement.util.CustomBeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static org.example.ordermanagement.exception.CommonApiResultCode.INVALID_ORDER_ID;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    public final PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Payment payment) {
        Long currentTime = System.currentTimeMillis();
        payment.setCreatedDate(currentTime);
        payment.setUpdatedDate(currentTime);
        if (Objects.isNull(payment.getTxnDate())) {
            payment.setTxnDate(currentTime);
        }
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        Payment paymentFromDb =  getPaymentById(payment.getId());
        if (Objects.nonNull(paymentFromDb)) {
            CustomBeanUtils.copyNonNullProperties(payment, paymentFromDb);
            paymentFromDb.setUpdatedDate(System.currentTimeMillis());
        } else {
            throw BixException.of(INVALID_ORDER_ID);
        }
        return paymentRepository.saveAndFlush(paymentFromDb);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Payment> findPaymentsByOrderId(Long orderId) {
        return paymentRepository.findAllByOrderId(orderId);
    }

}
