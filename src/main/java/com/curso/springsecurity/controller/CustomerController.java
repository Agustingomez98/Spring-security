package com.curso.springsecurity.controller;

import com.curso.springsecurity.dto.RegisteredUser;
import com.curso.springsecurity.dto.SaveUser;
import com.curso.springsecurity.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<RegisteredUser> registeredUser (@RequestBody @Valid SaveUser newUser){
        RegisteredUser registeredUser = authenticationService.registerOneCustomer(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

}
