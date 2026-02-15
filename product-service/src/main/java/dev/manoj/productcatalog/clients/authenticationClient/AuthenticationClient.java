package dev.manoj.productcatalog.clients.authenticationClient;

import dev.manoj.productcatalog.clients.authenticationClient.dtos.*;
import dev.manoj.productcatalog.clients.fakeStoreApi.FakeStoreProductDto;
import lombok.AllArgsConstructor;
//import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class AuthenticationClient {
//    private RestTemplateBuilder restTemplateBuilder;
    public ValidateTokenResponseDTO validate(String token, Long userId){
//        RestTemplate restTemplate = restTemplateBuilder.build();
        RestTemplate restTemplate = new RestTemplate();
        ValidateTokenRequestDTO request = new ValidateTokenRequestDTO();
        request.setToken(token);
        request.setUserId(userId);

        ResponseEntity<ValidateTokenResponseDTO> l = restTemplate.postForEntity(
                "http://localhost:8000/auth/validate",
                request,
                ValidateTokenResponseDTO.class
        );
        return l.getBody();



//        UserDTO userDto=new UserDTO();
//        userDto.setEmail("prvn@email.com");
//        Role role=new Role("ADMIN");
//        List<Role> userRoles = new ArrayList<>();
//        userRoles.add(role);
//        userDto.setRoles(userRoles);
//        System.out.println("GET ROLES : "+userDto.getRoles());
//        ValidateTokenResponseDTO resultDTO=new ValidateTokenResponseDTO();
//        resultDTO.setUserDTO(userDto);
//        resultDTO.setSessionStatus(SessionStatus.ACTIVE);
//
//
//        return resultDTO;
    };



}
