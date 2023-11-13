package com.curso.springsecurity.service.auth;

import com.curso.springsecurity.dto.RegisteredUser;
import com.curso.springsecurity.dto.SaveUser;
import com.curso.springsecurity.dto.auth.AuthenticationResponse;
import com.curso.springsecurity.dto.auth.Login;
import com.curso.springsecurity.entities.User;
import com.curso.springsecurity.exception.NotFoundException;
import com.curso.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    public RegisteredUser registerOneCustomer(SaveUser newUser) {

        User user = userService.registerOneCustomer(newUser); //Se guarda en la base de datos.

        RegisteredUser userDto = new RegisteredUser(); //Mapeo de los datos al DTO
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());

        String jwt = jwtService.generateToken(user,generateExtraClaims(user));
        userDto.setJwt(jwt);
        return userDto;
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put("full name",user.getFullName());
        extraClaims.put("role",user.getRole().name());
        extraClaims.put("authorities",user.getAuthorities());
        return extraClaims;
    }

    public AuthenticationResponse login(Login login) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword());

        authenticationManager.authenticate(authentication);

        UserDetails user = userService.findByEmail(login.getEmail()).get();
        String jwt = jwtService.generateToken(user,generateExtraClaims((User) user));

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setJwt(jwt);

        return authenticationResponse;
    }

    public boolean validateToken(String jwt){
        try {
            jwtService.extractUsername(jwt);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public User findLoggedInUser() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        String username = (String) authentication.getPrincipal();
        return userService.findByEmail(username).orElseThrow(()-> new NotFoundException("User not found"));

    }
}
