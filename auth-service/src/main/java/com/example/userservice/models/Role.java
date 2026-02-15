package com.example.userservice.models;


import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseModel{
    private String roleName;

    public String toString(){
        return "Role : "+roleName;
    }
}
