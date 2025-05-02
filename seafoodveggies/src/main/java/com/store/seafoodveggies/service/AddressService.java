package com.store.seafoodveggies.service;

import com.store.seafoodveggies.entity.Address;

public interface AddressService {

    void saveAddress(Address address);
    Address updateAddress(Address address);
    void deleteAddress(Address address);
    Address updateAddress(int userId, Long addressId, Address updatedAddress);
    void deleteAddress(int userId, Long addressId);

}
