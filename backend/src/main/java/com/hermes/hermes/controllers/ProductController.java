package com.hermes.hermes.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hermes.hermes.dto.CreateProductDto;
import com.hermes.hermes.dto.ProductResponseDto;
import com.hermes.hermes.entities.Product;
import com.hermes.hermes.entities.User;
import com.hermes.hermes.services.ProductService;

import jakarta.validation.Valid;

@RequestMapping("/api/v1/products")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody CreateProductDto dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Product product = productService.createProduct(
            user,
            dto.getName(),
            dto.getDescription(),
            dto.getPrice(),
            dto.getImageUrl(),
            dto.getQuantity()
        );
        return ResponseEntity.status(201).body(toResponse(product));
    }

    @GetMapping("/mine")
    public ResponseEntity<List<ProductResponseDto>> getMyProducts() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ProductResponseDto> products = productService.getMyProducts(user)
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @Valid @RequestBody CreateProductDto dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Product product = productService.updateProduct(
            user,
            id,
            dto.getName(),
            dto.getDescription(),
            dto.getPrice(),
            dto.getImageUrl(),
            dto.getQuantity()
        );
        return ResponseEntity.ok(toResponse(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        productService.deleteProduct(user, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(products);
    }

    private ProductResponseDto toResponse(Product product) {
        return new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getImageUrl(),
            product.getQuantity()
        );
    }
}