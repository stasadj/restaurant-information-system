package com.restaurant.backend.repository;

import java.util.Optional;

import com.restaurant.backend.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(long id);

    Optional<User> findByUsername(String username);
}
