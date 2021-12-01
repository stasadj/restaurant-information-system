package com.restaurant.backend.repository;

import java.util.Optional;

import com.restaurant.backend.domain.Staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByPin(int pin);

    @Modifying
    @Query(value = "UPDATE user u SET u.pin = :newPin WHERE u.pin = :currentPin", nativeQuery = true)
    void setNewPinForStaff(@Param("newPin") Integer newPin, @Param("currentPin") Integer currentPin);
}
