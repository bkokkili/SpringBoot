package com.store.seafoodveggies.service;

import com.store.seafoodveggies.entity.Address;
import com.store.seafoodveggies.entity.User;
import com.store.seafoodveggies.repo.AddressRepository;
import com.store.seafoodveggies.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

        addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Address address) {
        return null;
    }

    @Override
    public void deleteAddress(Address address) {

    }

    @Override
    public Address updateAddress(Long userId, Long addressId, Address updatedAddress) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        List<Address> existingAdresses = user.getAddresses();
        LOG.info("*******Address****"+existingAdresses.size());

        Address existingAddress = user.getAddresses().stream()
                .filter(address -> address.getId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId + " for user id: " + userId));

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

        return addressRepository.save(existingAddress);
    }

    @Override
    public void deleteAddress(Long userId, Long addressId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Address addressToRemove = user.getAddresses().stream()
                .filter(address -> Objects.equals(address.getId(), addressId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId + " for user id: " + userId));

        user.getAddresses().remove(addressToRemove);
        addressRepository.delete(addressToRemove);  // Optional: if orphan removal is enabled, this may not be required
        userRepository.save(user); // Persist change
    }

}
