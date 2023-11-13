package com.curso.springsecurity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
@Data
public class SaveCategory implements Serializable {
    @NotBlank
    private String name;
}
