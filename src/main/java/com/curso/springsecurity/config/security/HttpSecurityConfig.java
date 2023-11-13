package com.curso.springsecurity.config.security;

import com.curso.springsecurity.config.security.filter.JwtAuthenticationFilter;
import com.curso.springsecurity.entities.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //Activa y configura componentes de manera predeterminada
/*
@EnableMethodSecurity(prePostEnabled = true) Seguridad basada en metodos
*/
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider daoAuthenticationProvider;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception { //HttpSecurity permite personalizar como se van a gestionar y proteger las solicitudes http (autorizar rutas,agregar roles,configurar cierto filtros y sus ordenes)

        SecurityFilterChain  securityFilterChain = http
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessMagConfig -> sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Se configura el tipo de sesion que vamos a tener. Recibe una enumeracion, se elige STATELESS que define que es una aplicacion sin estado.Esa enumeracion representa diferentes politicas para la creacion de sesiones.
                .authenticationProvider(daoAuthenticationProvider) //Configura la estrategia de autenticacion
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests(authReqConfig-> { //Rutas publicas

                    /*
                    Autorizacion de endpoints de products
                     */

                    authReqConfig.requestMatchers(HttpMethod.GET,"/products")
                            .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name()); //Autorizacion por medio de roles
                            /*.hasAuthority(RolePermission.READ_ALL_PRODUCTS.name());*/                           //Autorizacion por medio de permisos

                    authReqConfig.requestMatchers(HttpMethod.GET,"/products/{productId}")
                            .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name());
                            /*.hasAuthority(RolePermission.READ_ONE_PRODUCT.name());*/
                    authReqConfig.requestMatchers(HttpMethod.POST,"/products")
                            .hasAnyRole(Role.ADMINISTRATOR.name());
                            /*.hasAuthority(RolePermission.CREATE_ONE_PRODUCT.name());*/
                    authReqConfig.requestMatchers(HttpMethod.PUT,"/products/{productId}")
                            .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name());
                            /*.hasAuthority(RolePermission.UPDATE_ONE_PRODUCT.name());*/
                    authReqConfig.requestMatchers(HttpMethod.PUT,"/products/{productId}/disabled")
                            .hasAnyRole(Role.ADMINISTRATOR.name());
                            /*.hasAuthority(RolePermission.DISABLE_ONE_PRODUCT.name());*/
                    /*
                    Autorizacion de endpoints de category
                     */

                    authReqConfig.requestMatchers(HttpMethod.GET,"/categories")
                            .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name());
                            /*.hasAuthority(RolePermission.READ_ALL_CATEGORIES.name());*/
                    authReqConfig.requestMatchers(HttpMethod.GET,"/categories/{categoryId}")
                            .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name());
                            /*.hasAuthority(RolePermission.READ_ONE_CATEGORY.name());*/
                    authReqConfig.requestMatchers(HttpMethod.POST,"/categories")
                            .hasAnyRole(Role.ADMINISTRATOR.name());
                            /*.hasAuthority(RolePermission.CREATE_ONE_CATEGORY.name());*/
                    authReqConfig.requestMatchers(HttpMethod.PUT,"/categories/{categoryId}")
                            .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name());
                            /*.hasAuthority(RolePermission.UPDATE_ONE_CATEGORY.name());*/
                    authReqConfig.requestMatchers(HttpMethod.PUT,"/categories/{categoryId}/disabled")
                            .hasAnyRole(Role.ADMINISTRATOR.name());
                            /*.hasAuthority(RolePermission.DISABLE_ONE_CATEGORY.name());*/
                    authReqConfig.requestMatchers(HttpMethod.GET,"/auth/profile")
                            .hasAnyRole(Role.ADMINISTRATOR.name(), Role.ASSISTANT_ADMINISTRATOR.name(), Role.CUSTOMER.name());
                            /*.hasAuthority(RolePermission.READ_MY_PROFILE.name());*/
                    /*
                    Autorizacion de endpoints publicos
                     */
                    authReqConfig.requestMatchers(HttpMethod.POST,"/customers").permitAll();
                    authReqConfig.requestMatchers(HttpMethod.POST,"/auth/login").permitAll();
                    authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate").permitAll();

                    authReqConfig.anyRequest().authenticated();
                })
                .exceptionHandling(exceptionConfig -> {
                    exceptionConfig.authenticationEntryPoint(authenticationEntryPoint);
                    exceptionConfig.accessDeniedHandler(accessDeniedHandler);
                })
                .build();
        return securityFilterChain;
    }

}
