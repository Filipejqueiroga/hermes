package com.hermes.hermes.controllers;

import com.hermes.hermes.dto.LoginResponse;
import com.hermes.hermes.dto.LoginUserDto;
import com.hermes.hermes.dto.RegisterUserDto;
import com.hermes.hermes.dto.VerifyUserDto;
import com.hermes.hermes.entities.User;
import com.hermes.hermes.services.AuthenticationService;
import com.hermes.hermes.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        String jwtToken = jwtService.generateToken(registeredUser);
        LoginResponse response = new LoginResponse(
            jwtToken,
            jwtService.getExpirationTime(),
            registeredUser.getId(),
            registeredUser.getName(),
            registeredUser.getEmail(),
            registeredUser.getRole()
        );
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(
            jwtToken,
            jwtService.getExpirationTime(),
            authenticatedUser.getId(),
            authenticatedUser.getName(),
            authenticatedUser.getEmail(),
            authenticatedUser.getRole()
        );
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}