package com.example.userservice.services;

import com.example.userservice.dtos.AssignRoleDTO;
import com.example.userservice.dtos.UserDTO;
import com.example.userservice.exceptions.InvalidRoleException;
import com.example.userservice.exceptions.RoleAlreadyExistException;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import com.example.userservice.repositories.RoleRepository;
import com.example.userservice.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public  ResponseEntity<UserDTO> getUserDetails(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(user -> new ResponseEntity<>(
                user.toUserDTO(),
                HttpStatus.OK
        )).orElseGet(() -> new ResponseEntity<>(
                null,
                HttpStatus.NOT_FOUND
        ));

    }

    public ResponseEntity<UserDTO> assignRolesToUser(Long id, List<String> roleNameList) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty())
            return new ResponseEntity<>(
                    null,
                    HttpStatus.NOT_FOUND
            );
        User user = optionalUser.get();

        List<Role> userRoles = userRepository.findRoleById(id);
        List<String> userRoleNames=new ArrayList<>();
        List<String> assignRoleNames = new ArrayList<>();
        for(String roleName : roleNameList){
            boolean checkRoleIsPresent = roleRepository.existsByRoleName(roleName);
            System.out.println("Check Role : "+checkRoleIsPresent);
            if(!checkRoleIsPresent)
                throw new InvalidRoleException(roleName+" is not a valid role.");
            assignRoleNames.add(roleName);
        }
        for(Role role : userRoles){
            userRoleNames.add(role.getRoleName());
        }
        List<Role> addedRoles = new ArrayList<>();
        for(String assignRole : assignRoleNames){
            if(!userRoleNames.contains(assignRole)){
                Role role = roleRepository.findByRole_Name(assignRole);
                if (role == null) {
                    throw new InvalidRoleException(assignRole + " is not a valid role.");
                }
                addedRoles.add(role);

            }
        }
        System.out.println("Assign Role Names "+assignRoleNames);
        System.out.println("added roles "+addedRoles);
        System.out.println("Before user email : "+user.getEmail());
//        user.setEmail("Mee@gmail.com");
//        user.getRoles().addAll(addedRoles);

        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(
                savedUser.toUserDTO(),
                HttpStatus.OK
        );
    }
}
