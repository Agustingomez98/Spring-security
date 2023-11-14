package com.curso.springsecurity.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class ShowPermission implements Serializable {
    private Long id;
    private String httpMethod;
    private String operation;
    private String module;
    private String role;

}
