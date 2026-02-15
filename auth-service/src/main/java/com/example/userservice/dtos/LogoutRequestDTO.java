package com.example.userservice.dtos;

import lombok.Getter;

@Getter

public class LogoutRequestDTO {
    private Long userId;
    private String token;
}
