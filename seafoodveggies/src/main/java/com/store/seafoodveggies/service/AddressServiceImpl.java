package com.store.seafoodveggies.service;

import com.store.seafoodveggies.entity.Address;
import com.store.seafoodveggies.entity.User;
import com.store.seafoodveggies.exception.ResourceNotFoundException;
import com.store.seafoodveggies.repo.AddressRepository;
import com.store.seafoodveggies.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AddressServiceImpl implements AddressService{
    private static  final Logger LOG = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveAddress(Address address) {
        try {
            addressRepository.save(address);
        } catch (Exception e) {
            LOG.error("Failed to save address", e);
            throw new RuntimeException("Failed to save address");
        }
    }

    @Override
    public Address updateAddress(Long userId, Long addressId, Address updatedAddress) {
        LOG.info("Updating address ID {} for user ID {}", addressId, userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Address existingAddress = user.getAddresses().stream()
                .filter(address -> address.getId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId + " for user id: " + userId));

        existingAddress.setFullName(updatedAddress.getFullName());
        existingAddress.setPhoneNumber(updatedAddress.getPhoneNumber());
        existingAddress.setFlatOrHouseNumber(updatedAddress.getFlatOrHouseNumber());
        existingAddress.setAddressLine1(updatedAddress.getAddressLine1());
        existingAddress.setAddressLine2(updatedAddress.getAddressLine2());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setState(updatedAddress.getState());
        existingAddress.setPostalCode(updatedAddress.getPostalCode());
        existingAddress.setCountry(updatedAddress.getCountry());
        existingAddress.setIsDefault(updatedAddress.getIsDefault());

        try {
            Address saved = addressRepository.save(existingAddress);
            LOG.info("Address updated successfully for address ID {}", saved.getId());
            return saved;
        } catch (Exception e) {
            LOG.error("Failed to update address", e);
            throw new RuntimeException("Failed to update address");
        }
    }

    @Override
    public void deleteAddress(Long userId, Long addressId) {
        LOG.info("Deleting address ID {} for user ID {}", addressId, userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Address addressToRemove = user.getAddresses().stream()
                .filter(address -> Objects.equals(address.getId(), addressId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId + " for user id: " + userId));

        try {
            user.getAddresses().remove(addressToRemove);
            addressRepository.delete(addressToRemove); // Optional if orphanRemoval works
            userRepository.save(user);
            LOG.info("Address deleted successfully: {}", addressId);
        } catch (Exception e) {
            LOG.error("Failed to delete address", e);
            throw new RuntimeException("Failed to delete address");
        }
    }

}
