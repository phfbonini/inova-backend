package com.example.inova_backend.enums;

public enum Role {
    ADMIN("admin"),
    ESTUDANTE("estudante"),
    EMPRESA("empresa"),
    UNIVERSIDADE("universidade");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
