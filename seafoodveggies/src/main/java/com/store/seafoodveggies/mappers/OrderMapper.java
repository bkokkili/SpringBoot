package com.store.seafoodveggies.mappers;

import com.store.seafoodveggies.dto.AddressDTO;
import com.store.seafoodveggies.dto.OrderItemDTO;
import com.store.seafoodveggies.dto.OrderItemResponseDTO;
import com.store.seafoodveggies.dto.OrderResponseDTO;
import com.store.seafoodveggies.entity.Address;
import com.store.seafoodveggies.entity.Order;
import com.store.seafoodveggies.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponseDTO toOrderResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setUserName(order.getUser().getUserName());
        dto.setEmail(order.getUser().getEmail());
        dto.setShippingAddress(mapAddressToDTO(order.getShippingAddress()));
        dto.setItems(order.getItems().stream()
                .map(OrderMapper::mapOrderItemToDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private AddressDTO mapAddressToDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setFullName(address.getFullName());
        addressDTO.setPhoneNumber(address.getPhoneNumber());
        addressDTO.setFlatOrHouseNumber(address.getFlatOrHouseNumber());
        addressDTO.setAddressLine1(address.getAddressLine1());
        addressDTO.setAddressLine2(address.getAddressLine2());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setPostalCode(address.getPostalCode());
        addressDTO.setCountry(address.getCountry());
        return addressDTO;
    }

    private static OrderItemResponseDTO mapOrderItemToDTO(OrderItem item) {
        OrderItemResponseDTO dto = new OrderItemResponseDTO();
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPriceAtPurchase(item.getPriceAtPurchase());
        return dto;
    }
}
