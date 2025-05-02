package com.store.seafoodveggies.service;

import com.store.seafoodveggies.dto.OrderItemDTO;
import com.store.seafoodveggies.dto.OrderResponseDTO;

import java.util.List;


public interface OrderService {
     OrderResponseDTO placeOrder(Long userId, Long addressId, List<OrderItemDTO> itemsRequest);

     OrderResponseDTO updateOrderStatus(Long orderId, String status);

     List<OrderResponseDTO> getOrdersByUserId(Long userId);

     OrderResponseDTO getOrderDetails(Long userId, Long orderId);
}
