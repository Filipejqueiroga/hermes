package com.hermes.hermes.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hermes.hermes.entities.Store;
import com.hermes.hermes.entities.User;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>{
    Optional<Store> findBySlug(String slug);
    Optional<Store> findBySeller(User seller);
}
