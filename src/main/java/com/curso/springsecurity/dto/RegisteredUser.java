package com.curso.springsecurity.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class RegisteredUser implements Serializable {
    private Long id;
    private String fullName;
    private String email;
    private String role;
    private String jwt;
}
