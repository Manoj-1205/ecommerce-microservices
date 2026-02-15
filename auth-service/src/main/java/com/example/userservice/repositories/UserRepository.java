package com.example.userservice.repositories;

import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User save(User user);
    public Optional<User> findByEmail(String email);
    @Query("SELECT u.roles FROM User u WHERE u.id = :id")
    List<Role> findRoleById(Long id);

//    User findByIdAndRoleName(Long id, String roleName);
//
//    User findByIdAndRole_RoleName(Long id, String roleName);
}
