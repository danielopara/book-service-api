package com.daniel.bookservice.repository;

import com.daniel.bookservice.model.User;
import com.daniel.bookservice.model.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    long countByRole(Roles role);
}
