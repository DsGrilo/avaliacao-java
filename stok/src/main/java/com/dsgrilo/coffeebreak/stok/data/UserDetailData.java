package com.dsgrilo.coffeebreak.stok.data;

import com.dsgrilo.coffeebreak.stok.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

// implementação utilizada para fazer a validação de algumas informações do usuario

public class UserDetailData implements UserDetails {

    // utilizadao classe OPTIONAL para evitar NullPointerException
    private final Optional<UserModel> userModel;

    // construtor utilizado para injeção de depêndencia
    public UserDetailData(Optional<UserModel> userModel) {
        this.userModel = userModel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
            return  userModel.orElse(new UserModel()).getPassword();
    }

    @Override
    public String getUsername() {
        return userModel.orElse(new UserModel()).getLogin();
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
