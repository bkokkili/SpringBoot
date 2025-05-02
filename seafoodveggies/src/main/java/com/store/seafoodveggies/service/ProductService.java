package com.store.seafoodveggies.service;

import com.store.seafoodveggies.entity.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> saveProducts(List<Product> products);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct(Long id, Product updatedProduct);
    void deleteProduct(Long id);
}
