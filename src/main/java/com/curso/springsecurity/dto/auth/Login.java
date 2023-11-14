package com.curso.springsecurity.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
@Data
public class Login implements Serializable {

    @Email
    private String email;
    @NotBlank
    private String password;

}
