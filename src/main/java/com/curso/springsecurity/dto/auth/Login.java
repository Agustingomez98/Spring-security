package com.curso.springsecurity.dto.auth;

import lombok.Data;

import java.io.Serializable;
@Data
public class Login implements Serializable {

    private String email;
    private String password;

}
