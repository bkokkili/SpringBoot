package com.springdemo.readjsonfileexample.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
@Getter
@Setter
@Component
public class Product {
    private int id;
    private String title;
    private String description;
    private int price;
    private float discountPercentage;
    private float rating;
    private String stock;
    private String brand;
    private String category;
    private String thumbnail;
    private List<String> images;

}
