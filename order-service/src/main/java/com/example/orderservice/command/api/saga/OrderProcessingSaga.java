package com.example.orderservice.command.api.saga;

import com.example.common.command.*;
import com.example.common.event.*;
import com.example.common.model.User;
import com.example.common.query.GetUserPaymentDetailQuery;
import com.example.orderservice.command.api.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class OrderProcessingSaga {
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;

    public OrderProcessingSaga() {
    }

    @StartSaga
    @SagaEventHandler(associationProperty =  "orderId")
    public void handler (OrderCreatedEvent event) {
        log.info("OrderCreatedEvent in saga for order id : "+ event.getOrderId());

        GetUserPaymentDetailQuery userPaymentDetailQuery = new GetUserPaymentDetailQuery(event.getUserId());
        User user = null;
        try {
             user = queryGateway.query(userPaymentDetailQuery, ResponseTypes.instanceOf(User.class)).join();
             log.info(user.toString());
        }catch (Exception e){
            log.error(e.getMessage());
            cancelOrderCommand(event.getOrderId());
            //start compensation transaction
        }
        System.out.println(user);
        ValidatePaymentCommand paymentCommand = new ValidatePaymentCommand();
        paymentCommand.setPaymentId(UUID.randomUUID().toString());
        paymentCommand.setCardDetails(user.getCardDetails() );
        paymentCommand.setOrderId(event.getOrderId());

        commandGateway.sendAndWait(paymentCommand);
    }

    private void cancelOrderCommand(String orderId) {
        CancelOrderCommand command = new CancelOrderCommand();
        command.setOrderId(orderId);
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handler(PaymentProcessedEvent event){
        log.info("PaymentProcessedEvent in saga for order id : "+ event.getOrderId());

        ShipOrderCommand shipOrderCommand = new ShipOrderCommand();
        shipOrderCommand.setOrderId(event.getOrderId());
        shipOrderCommand.setShipmentId(UUID.randomUUID().toString());
        try{
            if (true) throw new Exception("Deneme Exception");
            commandGateway.sendAndWait(shipOrderCommand);
        }catch (Exception e){
            log.error(e.getMessage());
            cancelPaymentCommand(event);
        }
    }

    private void cancelPaymentCommand(PaymentProcessedEvent event) {
        log.info("cancelPaymentCommand çalıştı");
        CancelPaymentCommand command = new CancelPaymentCommand();
        command.setPaymentId(event.getPaymentId());
        command.setOrderId(event.getOrderId());
        log.info(command.toString());
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handler(OrderShipmentEvent event){
        log.info("OrderShipmentEvent in saga for order id : "+ event.getOrderId());

        CompleteOrderCommand command = new CompleteOrderCommand();
        command.setOrderId(event.getOrderId());
        command.setOrderStatus("APPROVE");
        commandGateway.send(command);

    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handler(OrderCompletedEvent event){
        log.info("OrderCompletedEvent in saga for order id : "+ event.getOrderId());

    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCancelledEvent event){
        log.info("OrderCancelledEvent in saga for order id : "+ event.getOrderId());
    }
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentCancelledEvent event){
        log.info("PaymentCancelledEvent in saga for order id : "+ event.getOrderId());
        cancelOrderCommand(event.getOrderId());
    }
}
