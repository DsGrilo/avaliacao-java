package com.dsgrilo.coffeebreak.stok.repository;


import com.dsgrilo.coffeebreak.stok.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


// interface utilizada para abstrair a persistencia de dados  isolando as entidades
// que acessam o banco de dados

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {


    // Método utilizado para fazer a busca do Usuario pelo nome
    public Optional<UserModel> findByLogin(String login);

}
