package com.curso.springsecurity.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
@Data
public class SaveUser implements Serializable {

    @Size(min = 4)
    private String fullName;
    private String email;
    @Size(min = 8)
    private String password;
    @Size(min = 8)
    private String repeatedPassword;
}
