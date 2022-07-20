package com.example.orderservice.command.api.event;


import com.example.common.event.OrderCancelledEvent;
import com.example.common.event.OrderCompletedEvent;
import com.example.orderservice.command.api.data.Order;
import com.example.orderservice.command.api.data.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.AllowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventsHandler {
    private final OrderRepository orderRepository;

    public OrderEventsHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @AllowReplay(value = false)
    @EventHandler
    public void on(OrderCreatedEvent createdEvent){
        Order order = new Order();
        BeanUtils.copyProperties(createdEvent, order);
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCompletedEvent event){
        log.info("OrderCompletedEvent çalıştı.");
        Order order = orderRepository.findById(event.getOrderId()).get();
        order.setOrderStatus(event.getOrderStatus());
        orderRepository.save(order);
        log.info("OrderCompletedEvent tamamlandı.");

    }

    @EventHandler
    public void on(OrderCancelledEvent event){
        log.info("CancelOrderedEvent çalıştı.");
        Order order = orderRepository.findById(event.getOrderId()).get();
        order.setOrderStatus(event.getOrderStatus());
        orderRepository.save(order);
        log.info("CancelOrderedEvent tamamlandı.");
    }
}
