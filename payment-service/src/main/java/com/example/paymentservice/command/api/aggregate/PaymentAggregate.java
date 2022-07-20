package com.example.paymentservice.command.api.aggregate;

import com.example.common.command.CancelPaymentCommand;
import com.example.common.command.ValidatePaymentCommand;
import com.example.common.event.PaymentCancelledEvent;
import com.example.common.event.PaymentProcessedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
@Slf4j
@Aggregate
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus;

    public PaymentAggregate() {
    }
    @CommandHandler
    public PaymentAggregate(ValidatePaymentCommand command) {
        //validate payment details
        // Publish the payment processed event
        log.info("Executing ValidatePaymentCommand for orderId: {} paymentId:{}",
                command.getOrderId(), command.getPaymentId());

        PaymentProcessedEvent event = new PaymentProcessedEvent();
        event.setPaymentId(command.getPaymentId());
        event.setOrderId(command.getOrderId());
        AggregateLifecycle.apply(event);
        log.info("PaymentProcessedEvent Applied");
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent event){
        this.orderId = event.getOrderId();
        this.paymentId = event.getPaymentId();
        this.paymentStatus = "";
    }

    @CommandHandler
    public void handle(CancelPaymentCommand command){
        log.info("Executing ValidatePaymentCommand for orderId: {} paymentId:{}",
                command.getOrderId(), command.getPaymentId());
        PaymentCancelledEvent event = new PaymentCancelledEvent(command.getPaymentId(),
                command.getOrderId(), command.getPaymentStatus());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PaymentCancelledEvent event){
        this.paymentStatus = event.getPaymentStatus();
    }
}
