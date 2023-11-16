package com.curso.springsecurity.config.security.filter;


import com.curso.springsecurity.entities.security.JwtToken;
import com.curso.springsecurity.exception.NotFoundException;
import com.curso.springsecurity.repositories.security.JwtTokenRepository;
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
import java.util.Date;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenRepository jwtRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //1- Obtener authotization header
        //2- Obtener token
        String jwt = jwtService.extractJwtFromRequest(request);
        if (jwt == null || !StringUtils.hasText(jwt)){
            filterChain.doFilter(request,response);
            return;
        }
        //2.1 - Obtener token no expirado y valido desde base de datos

        Optional<JwtToken> token = jwtRepository.findByToken(jwt);
        boolean isValid = validateToken(token);
        if (!isValid){
            filterChain.doFilter(request,response);
            return;
        }

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

    private boolean validateToken(Optional<JwtToken> optionalToken) {
        if (!optionalToken.isPresent()){
            return false;
        }
        JwtToken token = optionalToken.get();
        Date now = new Date(System.currentTimeMillis());
        boolean isValid = token.isValid() && token.getExpiration().after(now);
        if (!isValid){
            updateTokenStatus(token);
        }

        return isValid;
    }

    private void updateTokenStatus(JwtToken token) {
        token.setValid(false);
        jwtRepository.save(token);
    }
}
