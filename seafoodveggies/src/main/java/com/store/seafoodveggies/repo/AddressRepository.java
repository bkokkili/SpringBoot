package com.store.seafoodveggies.repo;

import com.store.seafoodveggies.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
