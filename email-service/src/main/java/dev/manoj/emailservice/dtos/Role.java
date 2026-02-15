package dev.manoj.emailservice.dtos;


import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private String roleName;

    public String toString(){
        return "Role : "+roleName;
    }
}
