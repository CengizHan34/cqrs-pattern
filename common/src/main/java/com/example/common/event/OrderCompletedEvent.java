package com.example.common.event;

import lombok.Data;

@Data
public class OrderCompletedEvent  {
    private String orderId;
    private String orderStatus;
}
