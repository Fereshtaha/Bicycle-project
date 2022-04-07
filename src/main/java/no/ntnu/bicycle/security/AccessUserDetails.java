package no.ntnu.bicycle.security;

import no.ntnu.bicycle.model.Customer;
import no.ntnu.bicycle.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AccessUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final boolean isActive;
    private final List<GrantedAuthority> authorities = new LinkedList<>();

    public AccessUserDetails(Customer customer) {
        this.username = customer.getEmail();
        this.password = customer.getPassword();
        this.isActive = customer.isActive();
        this.convertRoles(customer.getRoles());
    }

    private void convertRoles(Set<Role> roles) {
        authorities.clear();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
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
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
