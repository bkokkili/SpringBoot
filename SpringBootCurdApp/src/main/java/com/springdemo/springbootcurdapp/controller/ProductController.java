package com.springdemo.springbootcurdapp.controller;

import com.springdemo.springbootcurdapp.model.Product;
import com.springdemo.springbootcurdapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    public ProductRepository productRepository;
    @PostMapping( "/saveProduct")
    public Product saveProduct(@RequestBody Product product){
        System.out.println("*****Product Name******"+product.getName());
        return productRepository.save(product);
    }
    @PostMapping( "/getProductInfo")
    public String getProductInfo(){
        return "Test successfully";
    }
    @RequestMapping("/getAllProducts")
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    @RequestMapping("/getProductDetails")
    public Optional<Product> getProduct(Long id){
        return productRepository.findById(id);
    }
    @PostMapping("/deleteProduct")
    public void deleteProduct (Long id){
        productRepository.deleteById(id);
    }
    @PostMapping("/updateProductDetails")
    public Product updateProductDetails(@RequestBody Product product){

        Optional<Product> productDetails = productRepository.findById((long) product.getId());
        if (productDetails.isPresent()){
            Product existingProduct = productDetails.get();
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setRating(product.getRating());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setReleaseDate(product.getReleaseDate());

            return productRepository.save(existingProduct);
        }
        return null;
    }
}
