package com.curso.springsecurity.config.security.filter;


import com.curso.springsecurity.exception.NotFoundException;
import com.curso.springsecurity.service.UserService;
import com.curso.springsecurity.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1-Obntener encabezado http llamado Authorization

        String authorizationHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return; //Este es un metodo de tipo void por lo que el return, retorna el control a quien mando a llamar al metodo actual
        }

        //2- Obtener token JWT desde el encabezado

        String jwt = authorizationHeader.split(" ")[1];

        //3- Obtener el subject / username desdee el token
        // esta accion a su vez valida el formato del token, firma y fecha de expiracion

        String username = jwtService.extractUsername(jwt);

        //4- Setear objeto authentication (representa al usuario logueado) dentro de security context holder
        UserDetails userDetails = userService.findByEmail(username).orElseThrow(()->new NotFoundException("User not found"));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken((username),null,userDetails.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //5- Ejecutar el registro de filtros
        filterChain.doFilter(request,response);
    }
}
