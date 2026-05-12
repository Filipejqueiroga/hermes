package com.hermes.hermes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hermes.hermes.entities.Product;
import com.hermes.hermes.entities.Store;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStore(Store store);
    Optional<Product> findByIdAndStore(Long id, Store store);
}