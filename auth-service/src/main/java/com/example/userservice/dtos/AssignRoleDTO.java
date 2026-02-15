package com.example.userservice.dtos;

import com.example.userservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class AssignRoleDTO {
    private List<String> roleNames;
}
