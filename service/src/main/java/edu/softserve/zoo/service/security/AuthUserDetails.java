package edu.softserve.zoo.service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * Represents the {@link edu.softserve.zoo.model.Employee} for security mechanisms.
 *
 * @author Ilya Doroshenko
 */
public class AuthUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private String token;
    private Set<GrantedAuthority> authorities;

    public AuthUserDetails(Long id, String username, String password, boolean enabled,
                           String token, Set<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.token = token;
        this.authorities = authorities;
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
        return enabled;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AuthUserDetails{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
