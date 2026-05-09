package com.hermes.hermes.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateStoreDto {
    
    @NotBlank(message = "Store name is required")
    private String name;
    private String description;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    
}
