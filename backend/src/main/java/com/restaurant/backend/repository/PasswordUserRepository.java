package com.restaurant.backend.repository;

import java.util.Optional;

import com.restaurant.backend.domain.PasswordUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordUserRepository extends JpaRepository<PasswordUser, Long> {
    Optional<PasswordUser> findByUsername(String username);
}
