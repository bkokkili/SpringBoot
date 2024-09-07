package com.springdemo.springbootcurdapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROD_SEQ")
    @SequenceGenerator(sequenceName = "PRODUCT_SEQ", allocationSize = 1, name="PROD_SEQ")
    private int id;
    @Column
    private String name;
    @Column
    private String category;
    @Column
    private String brand;
    @Column
    private double price;
    @Column
    private float rating;
    @Column
    private String description;
    @Column
    private Date releaseDate;

    public Product(String name, String category, String brand, double price, float rating, String description, Date releaseDate) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public Product() {

    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
