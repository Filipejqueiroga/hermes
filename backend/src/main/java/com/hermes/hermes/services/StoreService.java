package com.hermes.hermes.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.hermes.entities.Store;
import com.hermes.hermes.entities.User;
import com.hermes.hermes.exceptions.DuplicateStoreException;
import com.hermes.hermes.repositories.StoreRepository;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    public StoreService(StoreRepository storeRepository, UserService userService) {
        this.storeRepository = storeRepository;
        this.userService = userService;
    }

    @Transactional
    public Store createStore(String name, String description, User seller) {
        String slug = generateSlug(name);

        if (storeRepository.findBySlug(slug).isPresent()) {
            throw new DuplicateStoreException("A store with this name already exists");
        }

        if (storeRepository.findBySeller(seller).isPresent()) {
            throw new DuplicateStoreException("User already has a store");
        }

        Store store = new Store();
        store.setName(name);
        store.setSlug(slug);
        store.setDescription(description);
        store.setSeller(seller);

        Store savedStore = storeRepository.save(store);
        userService.changeBuyerToSeller(seller);

        return savedStore;
    }

    private String generateSlug(String name) {
        return name.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .trim()
                .replaceAll("\\s+", "-");
    }
}