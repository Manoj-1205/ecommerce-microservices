package com.example.userservice.models;

import com.example.userservice.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
//@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel {
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles=new ArrayList<>();

    public UserDTO toUserDTO(){
        return UserDTO.builder()
                .email(email)
                .roles(roles)
                .build();
    }
}
