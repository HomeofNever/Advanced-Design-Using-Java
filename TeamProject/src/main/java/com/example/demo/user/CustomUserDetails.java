package com.example.demo.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * The Customized UserDetails
 */
public class CustomUserDetails implements UserDetails {
    /**
     * The user attribute
     */
    private User user;

    /**
     * @param user expected user
     */
    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * @return null for the authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * @return the plain-text password
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * @return the username
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * @return true if the account is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return true if the account is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return true if the credential is not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return true if the user is enable
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
