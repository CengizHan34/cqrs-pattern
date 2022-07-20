package com.example.orderservice.command.api.aggregate;


import com.example.common.command.CancelOrderCommand;
import com.example.common.command.CompleteOrderCommand;
import com.example.common.event.OrderCancelledEvent;
import com.example.common.event.OrderCompletedEvent;
import com.example.orderservice.command.api.command.CreateOrderCommand;
import com.example.orderservice.command.api.event.OrderCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class OrderAggregate  {
    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private int quantity;
    private String orderStatus;

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        // validate and others
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(command, orderCreatedEvent);
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event){
        this.orderId = event.getOrderId();
        this.productId = event.getProductId();
        this.addressId = event.getAddressId();
        this.quantity = event.getQuantity();
        this.orderStatus = event.getOrderStatus();
        this.userId = event.getUserId();
    }

    @CommandHandler
    public void handle(CompleteOrderCommand command){
        System.out.println("command handler çalıştı");
        OrderCompletedEvent event = new OrderCompletedEvent();
        event.setOrderStatus(command.getOrderStatus());
        event.setOrderId(command.getOrderId());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderCompletedEvent event){
        this.orderId = event.getOrderId();
        this.orderStatus = event.getOrderStatus();
    }

    @CommandHandler
    public void handle(CancelOrderCommand command){
        OrderCancelledEvent event = new OrderCancelledEvent();
        event.setOrderId(command.getOrderId());
        event.setOrderStatus(command.getOrderStatus());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderCancelledEvent event){
        this.orderStatus = event.getOrderStatus();
        this.orderId = event.getOrderId();
    }

}
