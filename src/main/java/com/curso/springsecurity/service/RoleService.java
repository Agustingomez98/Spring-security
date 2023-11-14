package com.curso.springsecurity.service;

import com.curso.springsecurity.entities.security.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findDefaultRole();
}
