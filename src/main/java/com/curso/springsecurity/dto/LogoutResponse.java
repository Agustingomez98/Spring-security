package com.curso.springsecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LogoutResponse implements Serializable {

    private String message;
}
