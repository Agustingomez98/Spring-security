package com.curso.springsecurity.exception;

import com.curso.springsecurity.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerGenericException(Exception exception, HttpServletRequest httpServletRequest){
        ApiError apiError =  new ApiError();
        apiError.setBackendMessage(exception.getMessage());
        apiError.setUrl(httpServletRequest.getRequestURL().toString());
        apiError.setMethod(httpServletRequest.getMethod());
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setMessage("Error interno en el servidor");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handlerAccessDeniedException(AccessDeniedException exception, HttpServletRequest httpServletRequest){
        ApiError apiError =  new ApiError();
        apiError.setBackendMessage(exception.getMessage());
        apiError.setUrl(httpServletRequest.getRequestURL().toString());
        apiError.setMethod(httpServletRequest.getMethod());
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setMessage("No cuentas con los permisos necesarios para acceder a esta funcion. Por favor, cotacta al administrador si crees que esto es un error");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                    HttpServletRequest httpServletRequest){
        ApiError apiError =  new ApiError();
        apiError.setBackendMessage(exception.getMessage());
        apiError.setUrl(httpServletRequest.getRequestURL().toString());
        apiError.setMethod(httpServletRequest.getMethod());
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setMessage("Error error en la peticion enviada");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }


}
