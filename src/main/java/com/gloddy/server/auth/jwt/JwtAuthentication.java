package com.gloddy.server.auth.jwt;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

@RequiredArgsConstructor
public class JwtAuthentication implements Authentication {

    private final JwtUserAdapter userDetails;
    private final String userId;

    public static Authentication of(JwtUserAdapter userDetails, String userId) {
        return new JwtAuthentication(userDetails, userId);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return userDetails.getUsername();
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        return userDetails.getUser().getId();
    }

    @Override
    public boolean isAuthenticated() {
        return userDetails.isEnabled();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return userId.toString();
    }

    @Override
    public boolean implies(Subject subject) {
        return Authentication.super.implies(subject);
    }
}
