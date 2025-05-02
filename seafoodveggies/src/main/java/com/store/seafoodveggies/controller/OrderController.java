package com.store.seafoodveggies.controller;

import com.store.seafoodveggies.dto.OrderRequest;
import com.store.seafoodveggies.dto.OrderResponseDTO;
import com.store.seafoodveggies.entity.Order;
import com.store.seafoodveggies.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponseDTO order = orderService.placeOrder(
                orderRequest.getUserId(),
                orderRequest.getAddressId(),
                orderRequest.getItems()
        );
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUser(@PathVariable Long userId) {
        List<OrderResponseDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}/order/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderDetails(
            @PathVariable Long userId,
            @PathVariable Long orderId) {

        OrderResponseDTO response = orderService.getOrderDetails(userId, orderId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {
        OrderResponseDTO updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

}
