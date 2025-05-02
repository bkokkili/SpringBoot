package com.store.seafoodveggies.service;

import com.store.seafoodveggies.dto.AddressDTO;
import com.store.seafoodveggies.dto.OrderItemDTO;
import com.store.seafoodveggies.dto.OrderItemResponseDTO;
import com.store.seafoodveggies.dto.OrderResponseDTO;
import com.store.seafoodveggies.entity.*;
import com.store.seafoodveggies.exception.ResourceNotFoundException;
import com.store.seafoodveggies.mappers.OrderMapper;
import com.store.seafoodveggies.repo.AddressRepository;
import com.store.seafoodveggies.repo.OrderRepository;
import com.store.seafoodveggies.repo.ProductRepository;
import com.store.seafoodveggies.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static  final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override

    public OrderResponseDTO placeOrder(Long userId, Long addressId, List<OrderItemDTO> itemsRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("PLACED");

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (OrderItemDTO req : itemsRequest) {
            Product product = productRepository.findById(req.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + req.getProductId()));

            if (product.getStockQuantity() < req.getQuantity()) {
                throw new ResourceNotFoundException("Insufficient stock for product: " + product.getName());
            }

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(req.getQuantity());
            item.setPriceAtPurchase(product.getPrice() * req.getQuantity());
            item.setOrder(order);

            orderItems.add(item);
            totalAmount += item.getPriceAtPurchase();

            product.setStockQuantity(product.getStockQuantity() - req.getQuantity());
        }

        order.setTotalAmount(totalAmount);
        order.setItems(orderItems);
        orderRepository.save(order);  // Save order and cascade items if set

        // Map to DTO
        List<OrderItemResponseDTO> itemDTOs = orderItems.stream().map(item -> {
            OrderItemResponseDTO dto = new OrderItemResponseDTO();
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setQuantity(item.getQuantity());
            dto.setPriceAtPurchase(item.getPriceAtPurchase());
            return dto;
        }).collect(Collectors.toList());

        AddressDTO shippingAddressDTO = new AddressDTO(
                address.getFullName(), address.getPhoneNumber(),
                address.getFlatOrHouseNumber(), address.getAddressLine1(),
                address.getAddressLine2(), address.getCity(), address.getState(),
                address.getPostalCode(), address.getCountry()
        );

        return new OrderResponseDTO(
                order.getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getOrderStatus(),
                user.getUserName(),
                user.getEmail(),
                shippingAddressDTO,
                itemDTOs
        );
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(orderMapper::toOrderResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO getOrderDetails(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        if (!order.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied: Order does not belong to the user.");
        }

        return orderMapper.toOrderResponseDTO(order);
    }

    @Override
    public OrderResponseDTO updateOrderStatus(Long orderId, String status) {
        LOG.info("Updating status for order ID: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        String normalizedStatus = status.toUpperCase();
        if (!List.of("PENDING", "ACCEPTED", "CANCELLED", "DELIVERED", "SHIPPED").contains(normalizedStatus)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        order.setOrderStatus(normalizedStatus);
        try {
            orderRepository.save(order);
            LOG.info("Order saved successfully");
        } catch (Exception e) {
            LOG.error("Exception while saving order", e);
        }

        LOG.info("Order status updated to {}", normalizedStatus);
        return orderMapper.toOrderResponseDTO(order);
    }
}
