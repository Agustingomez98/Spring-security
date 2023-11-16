package com.curso.springsecurity.controller;

import com.curso.springsecurity.dto.LogoutResponse;
import com.curso.springsecurity.dto.auth.AuthenticationResponse;
import com.curso.springsecurity.dto.auth.Login;
import com.curso.springsecurity.entities.security.User;
import com.curso.springsecurity.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    //@CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (@RequestBody @Valid Login login){

        AuthenticationResponse response = authenticationService.login(login);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout (HttpServletRequest request){

        authenticationService.logout(request);
        return ResponseEntity.ok(new LogoutResponse("Logout succesfull"));
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String jwt){

        boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> findMyProfile(){
        User user = authenticationService.findLoggedInUser();
        return ResponseEntity.ok(user);
    }
}
