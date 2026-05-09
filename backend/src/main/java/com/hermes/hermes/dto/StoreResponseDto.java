package com.hermes.hermes.dto;

import java.time.LocalDateTime;

public class StoreResponseDto {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private LocalDateTime createdAt;

    public StoreResponseDto(Long id, String name, String slug, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
