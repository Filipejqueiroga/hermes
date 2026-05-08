package com.hermes.hermes.dto;

import com.hermes.hermes.enums.Role;

public class LoginResponse {
    private String token;
    private long expiresIn;
    private Long userId;
    private String name;
    private String email;
    private Role role;

    public LoginResponse(String token, long expiresIn, Long userId, String name, String email, Role role) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getToken() { return token; }
    public long getExpiresIn() { return expiresIn; }
    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
}