package com.store.seafoodveggies.dto;

import com.store.seafoodveggies.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String orderStatus;
    private String userName;
    private String email;
    private AddressDTO shippingAddress;
    private List<OrderItemResponseDTO> items;

}