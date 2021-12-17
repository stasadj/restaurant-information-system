package com.restaurant.backend.repository;

import com.restaurant.backend.domain.PasswordUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordUserRepository extends JpaRepository<PasswordUser, Long> {
    Optional<PasswordUser> findByUsername(String username);
}
