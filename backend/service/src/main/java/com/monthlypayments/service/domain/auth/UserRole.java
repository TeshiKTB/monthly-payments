package com.monthlypayments.service.domain.auth;

public enum UserRole {

    ADMIN("admin"), USER("user");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}