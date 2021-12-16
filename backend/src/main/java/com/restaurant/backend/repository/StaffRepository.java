package com.restaurant.backend.repository;

import com.restaurant.backend.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByPin(int pin);
}
