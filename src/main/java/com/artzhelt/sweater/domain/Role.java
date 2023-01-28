package com.artzhelt.sweater.domain;

import java.util.Arrays;

public enum Role {
    USER, ADMIN;

    public static boolean contains(String role) {
        return Arrays.stream(values())
                .map(Enum::name)
                .anyMatch(role::equals);
    }
}
