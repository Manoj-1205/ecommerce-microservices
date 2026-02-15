package dev.manoj.productcatalog.clients.authenticationClient.dtos;


import dev.manoj.productcatalog.dtos.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenResponseDTO {
    private UserDTO userDTO;
    private SessionStatus sessionStatus;
}
