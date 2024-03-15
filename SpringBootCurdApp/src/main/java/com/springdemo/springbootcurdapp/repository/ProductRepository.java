package com.springdemo.springbootcurdapp.repository;

import com.springdemo.springbootcurdapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
