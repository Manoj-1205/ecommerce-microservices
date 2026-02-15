package com.example.userservice.repositories;

import com.example.userservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role save(Role role);

    Optional<Role> findById(Long id);

    boolean existsByRoleName(String roleName);


    @Query("SELECT roleName FROM Role WHERE roleName = :name")
    Role findByRole_Name(String name);
}
