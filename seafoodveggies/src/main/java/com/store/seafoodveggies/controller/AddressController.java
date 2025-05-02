package com.store.seafoodveggies.controller;

import com.store.seafoodveggies.entity.Address;
import com.store.seafoodveggies.entity.User;
import com.store.seafoodveggies.exception.ResourceNotFoundException;
import com.store.seafoodveggies.repo.UserRepository;
import com.store.seafoodveggies.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private AddressService addressService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<String> addAddressToUser(@PathVariable Long userId, @RequestBody Address addressRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        addressRequest.setUser(user); // Set the owning side
        user.getAddresses().add(addressRequest); // Optional, if you maintain bidirectional consistency

        addressService.saveAddress(addressRequest);  // Saves and links automatically

        return ResponseEntity.status(HttpStatus.CREATED).body("Address added successfully for user: " + user.getUserName());

    }

    @PutMapping("/update/{userId}/{addressId}")
    public ResponseEntity<Address> updateAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @RequestBody Address addressRequest) {

        Address updated = addressService.updateAddress(userId, addressId, addressRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{userId}/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.ok("Address deleted successfully for user id: " + userId);
    }

}
