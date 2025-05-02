package com.store.seafoodveggies.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long productId;
    private String productName;
    private int quantity;
    private double priceAtPurchase;
}
