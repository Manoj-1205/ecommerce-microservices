package com.example.userservice.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SignUpRequestDTO {
    private String email;
    private String password;
}
