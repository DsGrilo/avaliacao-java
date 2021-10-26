package com.dsgrilo.coffeebreak.stok.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dsgrilo.coffeebreak.stok.data.UserDetailData;
import com.dsgrilo.coffeebreak.stok.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticatorFilter extends UsernamePasswordAuthenticationFilter {

    public static final int TOKEN_EXPIRATION = 600_000;
    public static final String TOKEN_PWD = "8792501f-330e-47d8-a673-f01813c7f63a";

    private final AuthenticationManager authenticationManager;


    public JWTAuthenticatorFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    // Método utilizado para executar a autenticação
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // Utilizado o jackson para fazer a leitura do arquivo json e converte-lo para nossa classe userModel
        try {
            UserModel userModel = new ObjectMapper().readValue(request.getInputStream(), UserModel.class);

            return  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userModel.getLogin(),
                    userModel.getPassword(),
                    // Lançado Array vazio pois não será trabalhado as permissões
                    new ArrayList<>()
            ));
        } catch (IOException e) {
            throw  new RuntimeException("Falha ao autenticar usuário", e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        UserDetailData userDetailData = (UserDetailData) authResult.getPrincipal();

         String token = JWT.create()
                 .withSubject(userDetailData.getUsername())
                 .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                 .sign(Algorithm.HMAC512(TOKEN_PWD));

         response.getWriter().write(token);
         response.getWriter().flush();

    }
}
