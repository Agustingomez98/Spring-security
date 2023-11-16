package com.curso.springsecurity.service.auth;

import com.curso.springsecurity.dto.RegisteredUser;
import com.curso.springsecurity.dto.SaveUser;
import com.curso.springsecurity.dto.auth.AuthenticationResponse;
import com.curso.springsecurity.dto.auth.Login;
import com.curso.springsecurity.entities.security.JwtToken;
import com.curso.springsecurity.entities.security.User;
import com.curso.springsecurity.exception.NotFoundException;
import com.curso.springsecurity.repositories.security.JwtTokenRepository;
import com.curso.springsecurity.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenRepository jwtRepository;

    public RegisteredUser registerOneCustomer(SaveUser newUser) {

        User user = userService.registerOneCustomer(newUser); //Se guarda en la base de datos.

        RegisteredUser userDto = new RegisteredUser(); //Mapeo de los datos al DTO
        userDto.setId(user.getId());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().getName());

        String jwt = jwtService.generateToken(user,generateExtraClaims(user));

        saveUserToken(user,jwt);

        userDto.setJwt(jwt);
        return userDto;
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put("full name",user.getFullName());
        extraClaims.put("role",user.getRole().getName());
        extraClaims.put("authorities",user.getAuthorities());
        return extraClaims;
    }

    public AuthenticationResponse login(Login login) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(login.getEmail(),login.getPassword());

        authenticationManager.authenticate(authentication);

        UserDetails user = userService.findByEmail(login.getEmail()).get();
        String jwt = jwtService.generateToken(user,generateExtraClaims((User) user));
        saveUserToken((User)user,jwt);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setJwt(jwt);

        return authenticationResponse;
    }

    private void saveUserToken(User user, String jwt) {
        JwtToken token = new JwtToken();
        token.setToken(jwt);
        token.setUser(user);
        token.setExpiration(jwtService.extractExpiration(jwt));
        token.setValid(true);
        jwtRepository.save(token);
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

    public void logout(HttpServletRequest request) {
        String jwt = jwtService.extractJwtFromRequest(request);
        if (jwt == null || !StringUtils.hasText(jwt))return; //En caso que el token este vacio o sea nulo devuelve el control al metodo y se realiza el logout

        Optional<JwtToken> token = jwtRepository.findByToken(jwt);
        if (token.isPresent() && token.get().isValid()){ //En caso de que el token sea valido lo paso a false y si no es valido devuelvo el control
            token.get().setValid(false);
            jwtRepository.save(token.get());
        }
    }
}
