package com.mse.wcp.forum.persistence.entity;

public enum Role {
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR"),
    USER("USER");

    private final String text;

    Role(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}