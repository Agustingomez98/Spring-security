package com.curso.springsecurity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
@Data
public class SavePermission implements Serializable {
    @NotBlank
    private String role;
    @NotBlank
    private String operation;
}
