package com.example.userservice.controllers;

import com.example.userservice.dtos.AssignRoleDTO;
import com.example.userservice.dtos.UserDTO;
import com.example.userservice.models.User;
import com.example.userservice.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable Long id){
        System.out.println("I got the request");

        return userService.getUserDetails(id);
    }


    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDTO> assignRolesToUser(@PathVariable Long id, @RequestBody AssignRoleDTO assignRoleDTO){
        //Add functionality like roles for admin need to be added only if it exists in DB
        return userService.assignRolesToUser(id, assignRoleDTO.getRoleNames());
    }
}
