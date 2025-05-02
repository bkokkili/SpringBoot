package com.store.seafoodveggies.service;

import com.store.seafoodveggies.entity.Address;

public interface AddressService {

    void saveAddress(Address address);
    Address updateAddress(Address address);
    void deleteAddress(Address address);
    Address updateAddress(Long userId, Long addressId, Address updatedAddress);
    void deleteAddress(Long userId, Long addressId);

}
