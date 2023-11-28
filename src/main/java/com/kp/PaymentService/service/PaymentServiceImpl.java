package com.kp.PaymentService.service;

import com.kp.PaymentService.entity.TransactionDetails;
import com.kp.PaymentService.model.PaymentMode;
import com.kp.PaymentService.model.PaymentRequest;
import com.kp.PaymentService.model.PaymentResponse;
import com.kp.PaymentService.repository.PaymentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Log4j2
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording Payment Details:{}", paymentRequest);
        TransactionDetails transactionDetails = TransactionDetails
                .builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .orderId(paymentRequest.getOrderId())
                .paymentStatus("PLACED")
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();

        paymentRepository.save(transactionDetails);

        log.info("Transaction Completed with Id:{}", transactionDetails.getId());

        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(long orderId) {
        log.info("Getting payment details for the Order Id: {}", orderId);
        TransactionDetails transactionDetails = paymentRepository.findByOrderId(orderId);

        PaymentResponse paymentResponse = PaymentResponse
                .builder()
                .paymentId(transactionDetails.getId())
                .status(transactionDetails.getPaymentStatus())
                .amount(transactionDetails.getAmount())
                .orderId(transactionDetails.getOrderId())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .paymentDate(transactionDetails.getPaymentDate())
                .build();
        return paymentResponse;
    }
}
