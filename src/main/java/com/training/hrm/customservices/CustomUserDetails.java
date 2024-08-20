package com.training.hrm.customservices;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private String userID;
    private String employeeID;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String userID, String employeeID, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.userID = userID;
        this.employeeID = employeeID;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public String getUserID() {
        return userID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

