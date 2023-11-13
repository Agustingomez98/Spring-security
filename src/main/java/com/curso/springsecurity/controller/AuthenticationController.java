package com.curso.springsecurity.controller;

import com.curso.springsecurity.dto.auth.AuthenticationResponse;
import com.curso.springsecurity.dto.auth.Login;
import com.curso.springsecurity.entities.User;
import com.curso.springsecurity.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (@RequestBody @Valid Login login){

        AuthenticationResponse response = authenticationService.login(login);

        return ResponseEntity.ok(response);

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
