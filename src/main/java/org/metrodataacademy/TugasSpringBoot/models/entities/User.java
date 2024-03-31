package org.metrodataacademy.TugasSpringBoot.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_user")
public class User implements UserDetails {

    @Id
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne
    @MapsId
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(
            name = "id",
            foreignKey = @ForeignKey(name = "fk_user1")
    )
    private Employee employee;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user2")),
            inverseJoinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_user3"))
    )
    private List<Role> roles;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public static User build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new User(user.getId(), user.getUsername(), user.getPassword(),
                user.getEmployee(), user.getRoles(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        roles.forEach(role -> {
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
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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