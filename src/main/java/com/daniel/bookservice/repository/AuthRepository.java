package com.daniel.bookservice.repository;

import com.daniel.bookservice.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {
}
