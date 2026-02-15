package dev.manoj.productcatalog.dtos;


import dev.manoj.productcatalog.clients.authenticationClient.dtos.Role;
import lombok.*;
import org.hibernate.annotations.AnyKeyJavaClass;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String email;
    private List<Role> roles;


}
