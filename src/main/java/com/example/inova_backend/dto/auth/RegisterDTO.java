package com.example.inova_backend.dto.auth;

import com.example.inova_backend.enums.Role;

public record RegisterDTO(String email, String password, Role role) {
}
