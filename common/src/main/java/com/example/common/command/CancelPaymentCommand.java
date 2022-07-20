package com.example.common.command;

import lombok.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelPaymentCommand implements Serializable {
    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus = "CANCELLED";
}
