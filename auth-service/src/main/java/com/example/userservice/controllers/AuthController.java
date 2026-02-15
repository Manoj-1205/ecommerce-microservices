package com.example.userservice.controllers;

import com.example.userservice.dtos.*;

import com.example.userservice.models.SessionStatus;
import com.example.userservice.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
       return authService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpRequestDTO signUpRequestDTO){
        return authService.signup(signUpRequestDTO);
    }


    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenResponseDTO> validate(@RequestBody ValidateTokenRequestDTO validateTokenRequestDTO){
        Optional<UserDTO> optionalUserDTO = authService.validateToken(validateTokenRequestDTO.getUserId(), validateTokenRequestDTO.getToken());

        if(optionalUserDTO.isEmpty()){
            ValidateTokenResponseDTO validateTokenResponseDTO=new ValidateTokenResponseDTO();
            validateTokenResponseDTO.setSessionStatus(SessionStatus.INVALID);
            return new ResponseEntity<>(validateTokenResponseDTO, HttpStatus.OK);
        }
        ValidateTokenResponseDTO validateTokenResponseDTO=new ValidateTokenResponseDTO();
        validateTokenResponseDTO.setSessionStatus(SessionStatus.ACTIVE);
        validateTokenResponseDTO.setUserDTO(optionalUserDTO.get());
        return new ResponseEntity<>(validateTokenResponseDTO, HttpStatus.OK);

    }


    @PostMapping("/logout")
    public SessionStatus logout(@RequestBody LogoutRequestDTO logoutRequestDTO){
        return authService.logout(logoutRequestDTO.getUserId(),logoutRequestDTO.getToken());
    }


}
