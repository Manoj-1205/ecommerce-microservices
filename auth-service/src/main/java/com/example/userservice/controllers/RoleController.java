package com.example.userservice.controllers;

import com.example.userservice.dtos.CreateRoleRequestDTO;
import com.example.userservice.models.Role;
import com.example.userservice.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody CreateRoleRequestDTO roleRequestDTO){
        Role role = Role.builder()
                .roleName(roleRequestDTO.getRoleName())
                .build();
        return roleService.createRole(role);

    }
}
