package com.dsgrilo.coffeebreak.stok.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Classe utilizada para fazer o instanciamento do encriptador

@Configuration
public class EncoderPassword {

    @Bean
    public PasswordEncoder getPasswordEncoder()
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }


}
