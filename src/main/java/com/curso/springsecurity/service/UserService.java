package com.curso.springsecurity.service;

import com.curso.springsecurity.dto.SaveUser;
import com.curso.springsecurity.entities.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User registerOneCustomer(SaveUser newUser);

    Optional<User> findByEmail(String email);
}
