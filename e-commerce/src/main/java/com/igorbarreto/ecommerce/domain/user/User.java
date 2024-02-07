package com.igorbarreto.ecommerce.domain.user;

import com.igorbarreto.ecommerce.domain.user.enums.Role;
import com.igorbarreto.ecommerce.dtos.UserRequestDTO;
import com.igorbarreto.ecommerce.dtos.UserResponseDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String login;

    private String password;

    private Double moneyAccount;

    private Role role;

    public User(String name, String login, String password, Role role) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(String name, String login, String password, Double moneyAccount, Role role) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.moneyAccount = moneyAccount;
        this.role = role;
    }

    public User(UserRequestDTO request) {
        this.name = request.name();
        this.login = request.login();
        this.password = request.password();
        this.moneyAccount = request.moneyAccount();
        this.role = Role.USER;
    }

    public User(UserResponseDTO response) {
        this.name = response.name();
        this.login = response.login();
    }
    public User() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public Role getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Double getMoneyAccount() {
        return moneyAccount;
    }

    public void setMoneyAccount(Double moneyAccount) {
        this.moneyAccount = moneyAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (this.role == Role.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }
        return List.of(
                new SimpleGrantedAuthority("ROLE_USER")
        );
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
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
