package com.curso.springsecurity.service.impl;

import com.curso.springsecurity.dto.SaveUser;
import com.curso.springsecurity.entities.security.Role;
import com.curso.springsecurity.entities.security.User;
import com.curso.springsecurity.exception.InvalidPasswordException;
import com.curso.springsecurity.exception.NotFoundException;
import com.curso.springsecurity.repositories.UserRepository;
import com.curso.springsecurity.service.RoleService;
import com.curso.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Override
    public User registerOneCustomer(SaveUser newUser) {
        
        validatePassword (newUser);
        Role defaultRole = roleService.findDefaultRole().orElseThrow(()-> new NotFoundException("Role not found. Default role"));

        User user = User.builder()
                .email(newUser.getEmail())
                .fullName(newUser.getFullName())
                .role(defaultRole)
                .password(passwordEncoder.encode(newUser.getPassword()))
                .build();

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private void validatePassword(SaveUser dto) {

        if (!StringUtils.hasText(dto.getPassword()) || !StringUtils.hasText(dto.getRepeatedPassword())){ // Valida que contenga texto
            throw new InvalidPasswordException("Password do not matches");
        }
        if (!dto.getPassword().equals(dto.getRepeatedPassword())){ //Que las contraseñas considan
            throw new InvalidPasswordException("Passwords dont match");
        }

    }
}
