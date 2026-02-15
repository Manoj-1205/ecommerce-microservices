package com.example.userservice.services;

import com.example.userservice.dtos.SignUpRequestDTO;
import com.example.userservice.dtos.UserDTO;
import com.example.userservice.exceptions.InvalidPasswordException;
import com.example.userservice.exceptions.UserAlreadyExistException;
import com.example.userservice.exceptions.UserDoesNotExistException;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.models.Session;
import com.example.userservice.models.SessionStatus;
import com.example.userservice.models.User;
import com.example.userservice.repositories.SessionRepository;
import com.example.userservice.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.util.*;

@Service

public class AuthService {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private PasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, PasswordEncoder bCryptPasswordEncoder){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }
    public ResponseEntity<UserDTO> login(String email, String password){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty())
            throw new UserDoesNotExistException("User doesn't exist found. Please Sign up");
        User user = optionalUser.get();

        //Check password matching or not
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new InvalidPasswordException("Invalid Password. please try again..");
        }

        String token = RandomStringUtils.randomAscii(20);
//        Header header = Jwts.header()
//                .add("User-type","ADMIN")
//                .build();
//
//        Claims claims = (Claims) Jwts.claims();
//        claims.put("userId",user.getEmail());
//        String jwtToken = Jwts .builder()
//                .claims(claims)
//                .signWith(SignatureAlgorithm.ES256,"Secret")


        Session session=new Session();
        session.setToken(token);
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
//        MultiValueMap<String, String> headers2 = new LinkedMultiValueMap<>();
        headers.add("AUTH_TOKEN", token);
        return new ResponseEntity<>(
                user.toUserDTO(),
                headers,
                HttpStatus.OK
        );
    }
    public Boolean validate(){
        return null;
    }



    public ResponseEntity<UserDTO> signup(SignUpRequestDTO signUpRequestDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(signUpRequestDTO.getEmail());
        if(optionalUser.isPresent())
            throw new UserAlreadyExistException("User already exists. Try log in..");
        User user=new User();

        user.setEmail(signUpRequestDTO.getEmail());

        String encodedPassword = bCryptPasswordEncoder.encode(signUpRequestDTO.getPassword());
        user.setPassword(encodedPassword);

        User savedUser = userRepository.save(user);

        return new ResponseEntity<>(
                savedUser.toUserDTO(),
                HttpStatus.OK
        );

    }

    public Optional<UserDTO> validateToken(Long userId, String token) {
        Optional<Session> optionalSession = sessionRepository.findByUser_IdAndToken(userId, token);

        if(optionalSession.isEmpty())
            return Optional.empty();
        Session session = optionalSession.get();
        System.out.println("Validation : "+session.getSessionStatus());
        //Checking the token is active or not
        if(!session.getSessionStatus().equals(SessionStatus.ACTIVE))
            return Optional.empty();

        User user = userRepository.findById(userId).get();
        UserDTO userDTO = user.toUserDTO();
        return Optional.of(userDTO);
    }

    public SessionStatus logout(Long userId, String token) {
        Optional<Session> optionalSession = sessionRepository.findByUser_IdAndToken(userId, token);
        if(optionalSession.isEmpty())
            return SessionStatus.INVALID;
        Session session = optionalSession.get();
        session.setSessionStatus(SessionStatus.LOGGED_OUT);
        sessionRepository.save(session);
        return SessionStatus.LOGGED_OUT;
    }
}
