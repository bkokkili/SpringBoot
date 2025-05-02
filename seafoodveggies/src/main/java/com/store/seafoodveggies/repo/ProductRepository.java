package com.store.seafoodveggies.repo;

import com.store.seafoodveggies.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
