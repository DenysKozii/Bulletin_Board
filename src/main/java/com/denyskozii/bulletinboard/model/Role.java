package com.denyskozii.bulletinboard.model;

import org.springframework.security.core.GrantedAuthority;


/**
 * Date: 28.09.2020
 *
 * @author Denys Kozii
 */
public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
