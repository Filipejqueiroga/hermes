package com.hermes.hermes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    void shouldReturnAllProducts() {
        Product p1 = new Product(1, "T-shirt", "Black", new BigDecimal("49.90"), 10);
        Product p2 = new Product(2, "Pants", "Jeans", new BigDecimal("99.90"), 5);
        when(productRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnProductWhenIdExists() {
        Product product = new Product(1, "T-shirt", "Black", new BigDecimal("49.90"), 10);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1);

        assertEquals("T-shirt", result.getName());
        assertEquals(1, result.getId());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getProductById(99));
    }

    @Test
    void shouldCreateProductWhenDataIsValid() {
        Product product = new Product(null, "T-shirt", "Black", new BigDecimal("49.90"), 10);
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.createProduct(product);

        assertNotNull(result);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        Product product = new Product(null, "T-shirt", "Black", new BigDecimal("-1.00"), 10);

        assertThrows(RuntimeException.class, () -> productService.createProduct(product));
        verify(productRepository, never()).save(product);
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsNegative() {
        Product product = new Product(null, "T-shirt", "Black", new BigDecimal("49.90"), -1);

        assertThrows(RuntimeException.class, () -> productService.createProduct(product));
        verify(productRepository, never()).save(product);
    }

    @Test
    void shouldUpdateProductWhenExists() {
        Product existing = new Product(1, "T-shirt", "Black", new BigDecimal("49.90"), 10);
        Product updated = new Product(1, "New T-shirt", "White", new BigDecimal("39.90"), 5);
        when(productRepository.findById(1)).thenReturn(Optional.of(existing));
        when(productRepository.save(existing)).thenReturn(existing);

        Product result = productService.updateProduct(updated, 1);

        assertEquals("New T-shirt", result.getName());
        assertEquals("White", result.getDescription());
    }

    @Test
    void shouldDeleteProductWhenExists() {
        Product product = new Product(1, "T-shirt", "Black", new BigDecimal("49.90"), 10);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        productService.deleteProduct(1);

        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentProduct() {
        when(productRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.deleteProduct(99));
        verify(productRepository, never()).deleteById(99);
    }
}