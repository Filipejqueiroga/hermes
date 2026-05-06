package com.hermes.hermes.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hermes.hermes.entities.Product;
import com.hermes.hermes.repositories.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Integer id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product){
        if (product.getQuantity() < 0) {
            throw new RuntimeException("Quantity can not be negative");
        }
        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Price can not be negative");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Product product, Integer id){
        Product existing = getProductById(id);

        existing.setDescription(product.getDescription());
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setQuantity(product.getQuantity());

        return productRepository.save(existing);
        
    }

    public void deleteProduct(Integer id){
        getProductById(id); 
        productRepository.deleteById(id);
    }
}
