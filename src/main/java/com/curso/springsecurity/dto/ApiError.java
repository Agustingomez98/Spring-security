package com.curso.springsecurity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ApiError implements Serializable {

    private String backendMessage;
    private String message;
    private String url;
    private String method;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime timestamp;
}
