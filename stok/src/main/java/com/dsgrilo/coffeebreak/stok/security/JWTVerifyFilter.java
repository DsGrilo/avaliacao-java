package com.dsgrilo.coffeebreak.stok.security;

import antlr.Token;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


//  Clase responsável para fazer a validação da entrada do TIPO BASE 64 E VALIDAR O TKEN
public class JWTVerifyFilter extends BasicAuthenticationFilter {

    public static final String HEADER_ATTRIBUTE = "Authorization";
    public static final String HEADER_PREFIX = "Bearer ";



    public JWTVerifyFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String attribute = request.getHeader(HEADER_ATTRIBUTE);

        if(attribute == null || !attribute.startsWith(HEADER_PREFIX)){
            chain.doFilter(request, response);
            return;
        }

        String token = attribute.replace(HEADER_PREFIX, "");

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token){

        // RECRIA O TOKEN PARA FAZER A VALIDAÇÃO
        String user = JWT.require(Algorithm.HMAC512(JWTAuthenticatorFilter.TOKEN_PWD))
                .build()
                .verify(token)
                .getSubject();

        if(user == null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());

    }
}
