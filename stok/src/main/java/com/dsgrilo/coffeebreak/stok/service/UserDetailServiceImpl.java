package com.dsgrilo.coffeebreak.stok.service;


import com.dsgrilo.coffeebreak.stok.model.UserModel;
import com.dsgrilo.coffeebreak.stok.repository.UserRepository;
import com.dsgrilo.coffeebreak.stok.data.UserDetailData;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Classe utilizada para gerenciar a busca do login no repository


@Component
public class UserDetailServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;


    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }




    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<UserModel> user = userRepository.findByLogin(login);
        if(user.isEmpty()){
            throw  new UsernameNotFoundException("User ["+ login +"] not found");
        }

        return new UserDetailData(user);
    }



}
