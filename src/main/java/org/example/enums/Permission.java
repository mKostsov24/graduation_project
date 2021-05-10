package org.example.enums;

public enum Permission {
    USER("user:write"),
    MODER("user:moder");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
