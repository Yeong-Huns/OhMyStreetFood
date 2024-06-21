package org.omsf.user;

import lombok.Getter;


@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
	OWNER("ROLE_OWNER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
