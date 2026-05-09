package com.hermes.hermes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hermes.hermes.entities.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>{

}
