package com.curso.springsecurity.repositories.security;

import com.curso.springsecurity.entities.security.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken,Long> {
    Optional<JwtToken> findByToken(String jwt);
}
