package com.restaurant.backend.repository;

import java.util.Optional;

import com.restaurant.backend.domain.Staff;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findById(long id);

    Optional<Staff> findByPin(int pin);
}
