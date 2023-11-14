package com.curso.springsecurity.service.impl;

import com.curso.springsecurity.dto.SavePermission;
import com.curso.springsecurity.dto.ShowPermission;
import com.curso.springsecurity.entities.security.GrantedPermission;
import com.curso.springsecurity.entities.security.Operation;
import com.curso.springsecurity.entities.security.Role;
import com.curso.springsecurity.exception.NotFoundException;
import com.curso.springsecurity.repositories.security.OperationRepository;
import com.curso.springsecurity.repositories.security.PermissionRepository;

import com.curso.springsecurity.repositories.security.RoleRepository;
import com.curso.springsecurity.service.PermissionService;
import com.curso.springsecurity.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private OperationRepository operationRepository;

    @Override
    public Page<ShowPermission> findAll(Pageable pageable) {
        return permissionRepository.findAll(pageable).map(this::mapEntityToShowDto);
    }

    @Override
    public Optional<ShowPermission> findById(Long permissionId) {
        return permissionRepository.findById(permissionId).map(this::mapEntityToShowDto);
    }

    @Override
    public ShowPermission createOnePermission(SavePermission savePermission) {

        GrantedPermission grantedPermission = new GrantedPermission();

        Operation operation = operationRepository.findByName(savePermission.getOperation()).orElseThrow(()-> new NotFoundException("Operation not found with this name"));

        Role role = roleRepository.findByName(savePermission.getRole()).orElseThrow(()-> new NotFoundException("Role not found with this name"));

        grantedPermission.setOperation(operation);
        grantedPermission.setRole(role);
        permissionRepository.save(grantedPermission);
        return mapEntityToShowDto(grantedPermission);
    }

    @Override
    public ShowPermission deletePermission(Long permissionId) {

        GrantedPermission permission = permissionRepository.findById(permissionId).orElseThrow(()->new NotFoundException("Permission not found"));
        permissionRepository.delete(permission);
        return mapEntityToShowDto(permission);
    }

    private ShowPermission mapEntityToShowDto(GrantedPermission grantedPermission) {
        if(grantedPermission == null) return null;

        ShowPermission showDto = new ShowPermission();
        showDto.setId(grantedPermission.getId());
        showDto.setRole(grantedPermission.getRole().getName());
        showDto.setOperation(grantedPermission.getOperation().getName());
        showDto.setHttpMethod(grantedPermission.getOperation().getHttpMethod());
        showDto.setModule(grantedPermission.getOperation().getModule().getName());

        return showDto;
    }
}
