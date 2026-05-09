package com.hermes.hermes.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hermes.hermes.dto.UserResponseDto;
import com.hermes.hermes.entities.User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;


@RequestMapping("/api/v1")
@RestController
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserResponseDto response = new UserResponseDto(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            user.isEnabled(),
            user.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }
}
