package com.example.orderservice.command.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private String orderId;
    @Column(unique = true)
    private String productId;
    private String userId;
    private String addressId;
    private int quantity;
    private String orderStatus;
}
