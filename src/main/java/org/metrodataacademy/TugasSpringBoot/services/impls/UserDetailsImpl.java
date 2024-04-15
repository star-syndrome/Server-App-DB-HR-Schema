package org.metrodataacademy.TugasSpringBoot.services.impls;

import lombok.AllArgsConstructor;
import org.metrodataacademy.TugasSpringBoot.models.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        user.getRoles().forEach(role -> {
            String roles = "ROLE_" + role.getName();
            authorityList.add(new SimpleGrantedAuthority(roles));

            role.getPrivileges().forEach(privilege -> {
                String privileges = privilege.getName();
                authorityList.add(new SimpleGrantedAuthority(privileges));
            });
        });
        return authorityList;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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