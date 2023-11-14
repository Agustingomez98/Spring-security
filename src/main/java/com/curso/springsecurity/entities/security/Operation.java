package com.curso.springsecurity.entities.security;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String path;
    private String httpMethod;
    private boolean permitAll;
    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;
}
