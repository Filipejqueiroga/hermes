package com.hermes.hermes;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getALLProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Integer id){
        return productService.getProductById(id);
    }
    
    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }
    
    @PutMapping("/{id}")
    public Product updateProduct(@RequestBody Product product, @PathVariable Integer id){
        return productService.updateProduct(product, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id){
        productService.deleteProduct(id);
    }
}
