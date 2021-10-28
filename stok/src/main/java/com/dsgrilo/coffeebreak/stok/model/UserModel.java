package com.dsgrilo.coffeebreak.stok.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

// CLASSE UTILIZADA COMO MODELO DA ENTIDADE


// USADO ANNOTATIONS DO LOMBOK PARA GERAR OS GETTER E SETTERS
@Entity
@Setter
@Getter
@EqualsAndHashCode
public class UserModel {

    // @ID informa que este atributoi será o ID da entidade no banco de dados
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uiid_user = UUID.randomUUID();

    @NotBlank(message = "Login is Required")
    @Column(nullable = false, unique = true)
    private String login;

    @Email(message = "Invalid Email")
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    private Boolean isAdmin;


}


