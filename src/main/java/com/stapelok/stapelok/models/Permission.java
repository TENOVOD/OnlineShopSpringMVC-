package com.stapelok.stapelok.models;

public enum Permission {
    USER("users:all"),
    ADMIN("admin:all");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
