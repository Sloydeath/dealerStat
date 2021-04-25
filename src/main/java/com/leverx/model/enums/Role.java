package com.leverx.model.enums;


public enum Role {
    ADMIN("ADMIN"),
    ANONYMOUS("ANONYMOUS"),
    TRADER("TRADER");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }


}
