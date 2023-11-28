package com.kp.PaymentService.service;

import com.kp.PaymentService.model.PaymentRequest;
import com.kp.PaymentService.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
