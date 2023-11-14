package com.curso.springsecurity.service;

import com.curso.springsecurity.dto.SavePermission;
import com.curso.springsecurity.dto.ShowPermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PermissionService {
    Page<ShowPermission> findAll(Pageable pageable);

    Optional<ShowPermission> findById(Long permissionId);

    ShowPermission createOnePermission(SavePermission savePermission);

    ShowPermission deletePermission (Long permissionId);
}
