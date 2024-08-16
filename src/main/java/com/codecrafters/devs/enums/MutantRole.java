package com.codecrafters.devs.enums;

import lombok.Getter;

@Getter
public enum MutantRole {
    ADMIN("admin"),
    USER("user");

    private final String role;

    MutantRole(String role){
        this.role = role;
    }
}
