package com.hermes.hermes.controllers;

import com.hermes.hermes.repositories.StoreRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hermes.hermes.dto.CreateStoreDto;
import com.hermes.hermes.dto.StoreResponseDto;
import com.hermes.hermes.entities.User;
import com.hermes.hermes.services.StoreService;

import com.hermes.hermes.entities.Store;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RequestMapping("/api/v1/store")
@RestController
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService){
        this.storeService = storeService;
    }

    @PostMapping()
    public ResponseEntity<StoreResponseDto> createStore(@RequestBody CreateStoreDto createStoreDto) {

        User seller = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Store store = storeService.createStore(createStoreDto.getName(), createStoreDto.getDescription(), seller);
        
        StoreResponseDto response = new StoreResponseDto(
            store.getId(),
            store.getName(),
            store.getSlug(),
            store.getDescription(),
            store.getCreatedAt()
        );

        return ResponseEntity.status(201).body(response);
    }
    
    @GetMapping("/mine")
    public ResponseEntity<StoreResponseDto> myStore() {
        User seller = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Store store = storeService.getStoreBySeller(seller);

        StoreResponseDto response = new StoreResponseDto(
            store.getId(),
            store.getName(),
            store.getSlug(),
            store.getDescription(),
            store.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }
}
