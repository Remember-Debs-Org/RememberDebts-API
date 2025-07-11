package com.rememberdebtscode.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rememberdebtscode.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUserEmail(String userEmail);

    Optional<User> findByUserEmail(String userEmail);
}
