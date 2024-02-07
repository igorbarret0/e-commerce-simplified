package com.igorbarreto.ecommerce.domain.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Double price;
    private Integer inventory;
    @NotNull
    private Long categoryId;

    public Product() {}

    public Product(Long id, String name, String description, Double price, Integer inventory, Long categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inventory = inventory;
        this.categoryId = categoryId;
    }

    public Product(String name, String description, Double price, Integer inventory, Long categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.inventory = inventory;
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getInventory() {
        return inventory;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
