package org.omsf.security;

import lombok.Getter;


@Getter
public enum UserRole { // yunbin
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
	OWNER("ROLE_OWNER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
