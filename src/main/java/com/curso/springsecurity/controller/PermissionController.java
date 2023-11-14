package com.curso.springsecurity.controller;

import com.curso.springsecurity.dto.SavePermission;
import com.curso.springsecurity.dto.ShowPermission;
import com.curso.springsecurity.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public ResponseEntity<Page<ShowPermission>> findAll(Pageable pageable){

        Page<ShowPermission> permissions = permissionService.findAll(pageable);
        if (permissions.hasContent()){
            return ResponseEntity.ok(permissions);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<ShowPermission> findOneById(@PathVariable Long permissionId){

        Optional<ShowPermission> permission = permissionService.findById(permissionId);
        if (permission.isPresent()){
            return ResponseEntity.ok(permission.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ShowPermission> savePermission(@RequestBody @Valid SavePermission savePermission){

        ShowPermission permission = permissionService.createOnePermission(savePermission);

     return ResponseEntity.status(HttpStatus.CREATED).body(permission);
    }

    @DeleteMapping("{permissionId}")
    private ResponseEntity<ShowPermission> deletePermission(@PathVariable Long permissionId){
        Optional<ShowPermission> permission = permissionService.findById(permissionId);
        if (permission.isPresent()){
            return ResponseEntity.ok(permissionService.deletePermission(permissionId));
        }
        return ResponseEntity.notFound().build();
    }

}
