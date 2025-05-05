package com.example.inova_backend.dto;

import com.example.inova_backend.enums.Role;

public record RegisterDTO(String email, String password, Role role) {
}
