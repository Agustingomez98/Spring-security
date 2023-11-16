package com.curso.springsecurity.entities.security;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2048)
    private String token;
    private Date expiration;
    private boolean isValid;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
