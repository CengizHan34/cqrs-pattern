package com.example.shipmentservice.command.api.aggregate;

import com.example.common.command.ShipOrderCommand;
import com.example.common.event.OrderShipmentEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class ShipmentAggregate {
    @AggregateIdentifier
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;

    public ShipmentAggregate() {
    }

    @CommandHandler
    public ShipmentAggregate(ShipOrderCommand command) {
        OrderShipmentEvent event = new OrderShipmentEvent();
        BeanUtils.copyProperties(command,event);
        event.setShipmentStatus("COMPLETED");
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderShipmentEvent event){
        this.shipmentId = event.getShipmentId();
        this.orderId = event.getOrderId();
        this.shipmentStatus = event.getShipmentStatus();
    }
}
