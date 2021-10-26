package com.dsgrilo.coffeebreak.stok.security;

import com.dsgrilo.coffeebreak.stok.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity // informa que esta classe é um componente de segurança
public class JWTConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl userService;

    private final EncoderPassword encoderPassword;

    public JWTConfiguration(UserDetailServiceImpl userService, EncoderPassword encoderPassword) {
        this.userService = userService;
        this.encoderPassword = encoderPassword;
    }


    // Metodo utilizado para informar a classe base de implementação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoderPassword.getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Informo ao security que para a realização do Login não irá solicitar autenticação
        // addFilters utilizado para lançar as classes criadas de filtros de autenticação
        // disabilitado csrf por estar em ambiente dev
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/user/create" ).permitAll()
                .antMatchers(HttpMethod.POST,"/login" ).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticatorFilter(authenticationManager()))
                .addFilter(new JWTVerifyFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    // Permite que a aplicação receba requisições de outros Dominios
    @Bean // utilizado para informar que este metodo é um componente
    CorsConfigurationSource corsConfiguration(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();

        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
