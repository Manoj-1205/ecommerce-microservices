package com.example.userservice.dtos;

import com.example.userservice.models.SessionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenResponseDTO {
    private UserDTO userDTO;
    private SessionStatus sessionStatus;
}
