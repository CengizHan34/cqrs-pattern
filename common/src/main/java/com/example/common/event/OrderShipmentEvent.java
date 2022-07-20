package com.example.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderShipmentEvent {
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;
}
