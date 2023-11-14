package com.curso.springsecurity.repositories.security;

import com.curso.springsecurity.entities.security.GrantedPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<GrantedPermission,Long> {
}
