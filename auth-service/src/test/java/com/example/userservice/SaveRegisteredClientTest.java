package com.example.userservice;

import com.example.userservice.controllers.AuthController;
import com.example.userservice.dtos.SignUpRequestDTO;
import com.example.userservice.security.repositories.JpaRegisteredClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

@SpringBootTest
public class SaveRegisteredClientTest {

    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;
    @Autowired
    private JpaRegisteredClientRepository jpaRegisteredClientRepository;
    @Autowired
    private AuthController authController;


    @Test
    public void saveRegisteredClientInDb() {
        RegisteredClient feClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("api-client2")
                .clientSecret(bcryptPasswordEncoder.encode("secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://oauth.pstmn.io/v1/callback")
                .postLogoutRedirectUri("http://127.0.0.1:8080/")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope(OidcScopes.EMAIL)
                .scope(OidcScopes.PHONE)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        jpaRegisteredClientRepository.save(feClient);
    }


    @Test
//    @Commit
    public void userSignupTest(){
        SignUpRequestDTO signUpRequestDTO = SignUpRequestDTO.builder()
                .email("sam@gmail.com")
                .password("password")
                .build();

        authController.signup(signUpRequestDTO);

    }
}
