package com.hermes.hermes.dto;

import java.math.BigDecimal;

public class ProductResponseDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Integer quantity;

    public ProductResponseDto(Long id, String name, String description, BigDecimal price, String imageUrl, Integer quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
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
    public BigDecimal getPrice() {
        return price;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public Integer getQuantity() {
        return quantity;
    }
}