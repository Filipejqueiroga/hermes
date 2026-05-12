package com.hermes.hermes.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hermes.hermes.entities.Product;
import com.hermes.hermes.entities.Store;
import com.hermes.hermes.entities.User;
import com.hermes.hermes.exceptions.ProductNotFoundException;
import com.hermes.hermes.exceptions.StoreNotFoundException;
import com.hermes.hermes.repositories.ProductRepository;
import com.hermes.hermes.repositories.StoreRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    public ProductService(ProductRepository productRepository, StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    private Store getStoreByUser(User user) {
        return storeRepository.findBySeller(user)
                .orElseThrow(() -> new StoreNotFoundException("Store not found"));
    }

    public List<Product> getMyProducts(User user) {
        Store store = getStoreByUser(user);
        return productRepository.findByStore(store);
    }

    public Product createProduct(User user, String name, String description, BigDecimal price, String imageUrl, Integer quantity) {
        Store store = getStoreByUser(user);

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);
        product.setQuantity(quantity);
        product.setStore(store);

        return productRepository.save(product);
    }

    public Product updateProduct(User user, Long id, String name, String description, BigDecimal price, String imageUrl, Integer quantity) {
        Store store = getStoreByUser(user);
        Product existing = productRepository.findByIdAndStore(id, store)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        existing.setName(name);
        existing.setDescription(description);
        existing.setPrice(price);
        existing.setImageUrl(imageUrl);
        existing.setQuantity(quantity);

        return productRepository.save(existing);
    }

    public void deleteProduct(User user, Long id) {
        Store store = getStoreByUser(user);
        Product product = productRepository.findByIdAndStore(id, store)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}