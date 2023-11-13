package com.curso.springsecurity.config.security;

import com.curso.springsecurity.exception.NotFoundException;
import com.curso.springsecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansInjector {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider(); //Estrategia de autenticacion que vamos a utilizar par

        authenticationStrategy.setPasswordEncoder(passwordEncoder());
        authenticationStrategy.setUserDetailsService(userDetailsService()); //Estrategia por la cual va a obtener el usuario de la base de datos.

        return authenticationStrategy;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){ //Esta interfaz implementa el metodo "loadUserByUsername" que devuelve un UserDetails que mi entidad "user" implementa.
        return (email)-> {
            return userRepository.findByEmail(email)
                    .orElseThrow(()->new NotFoundException("User not found with email "+email));
        };
    }

}
