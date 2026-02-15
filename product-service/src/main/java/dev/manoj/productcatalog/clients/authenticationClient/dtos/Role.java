package dev.manoj.productcatalog.clients.authenticationClient.dtos;


import dev.manoj.productcatalog.models.BaseModel;
import jakarta.persistence.Entity;
import lombok.*;


@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseModel {
    private String roleName;

    public String toString(){
        return "Role : "+roleName;
    }
}
