package com.hermes.hermes.services;

import org.springframework.stereotype.Service;

import com.hermes.hermes.entities.User;
import com.hermes.hermes.enums.Role;
import com.hermes.hermes.repositories.UserRepository;

@Service
public class UserService {
   private final UserRepository userRepository;
   
   public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
   }

    public void changeBuyerToSeller(User user) {
        user.setRole(Role.SELLER);
        userRepository.save(user);
    }
    
}
