package com.example.paymentservice.command.api.event;

import com.example.common.event.PaymentCancelledEvent;
import com.example.common.event.PaymentProcessedEvent;
import com.example.paymentservice.command.api.data.Payment;
import com.example.paymentservice.command.api.data.PaymentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PaymentEventHandler  {
    private PaymentRepository paymentRepository;

    public PaymentEventHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent event){
        Payment payment = new Payment();
        payment.setPaymentId(event.getPaymentId());
        payment.setOrderId(event.getOrderId());
        payment.setTimeStamp(new Date());
        payment.setPaymentStatus("COMPLETED");
        paymentRepository.save(payment);
    }

    @EventHandler
    public void on(PaymentCancelledEvent event){
        Payment payment = paymentRepository.findById(event.getPaymentId()).get();
        payment.setPaymentStatus(event.getPaymentStatus());
        paymentRepository.save(payment);
    }
}
