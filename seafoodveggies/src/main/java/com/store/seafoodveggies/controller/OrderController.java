package com.store.seafoodveggies.controller;

import com.store.seafoodveggies.dto.OrderRequest;
import com.store.seafoodveggies.dto.OrderResponseDTO;
import com.store.seafoodveggies.dto.OrderStatusUpdateRequest;
import com.store.seafoodveggies.entity.Order;
import com.store.seafoodveggies.exception.ResourceNotFoundException;
import com.store.seafoodveggies.mappers.OrderMapper;
import com.store.seafoodveggies.repo.OrderRepository;
import com.store.seafoodveggies.service.OrderService;
import com.store.seafoodveggies.util.PdfGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

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
            @RequestBody OrderStatusUpdateRequest request) {
        OrderResponseDTO updatedOrder = orderService.updateOrderStatus(orderId, request.getStatus());
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/invoice/{orderId}")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        OrderResponseDTO orderDTO = orderMapper.toOrderResponseDTO(order);

        try {
            byte[] pdf = PdfGeneratorUtil.generateOrderInvoice(orderDTO);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=order-" + orderId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF invoice", e);
        }
    }


}
