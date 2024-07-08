package org.omsf.security;

import lombok.Getter;

/**
* @packageName    : org.omsf.security
* @fileName       : UserRole.java
* @author         : leeyunbin
* @date           : 2024.06.18
* @description    :
* ===========================================================
* DATE              AUTHOR             NOTE
* -----------------------------------------------------------
* 2024.06.18        leeyunbin       최초 생성
*/

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
