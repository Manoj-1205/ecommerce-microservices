package com.example.userservice.repositories;

import com.example.userservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByUser_IdAndToken(Long userId, String token);
}
