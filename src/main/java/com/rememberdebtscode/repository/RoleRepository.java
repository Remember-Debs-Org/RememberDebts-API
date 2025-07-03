package com.rememberdebtscode.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rememberdebtscode.model.entity.Role;
import com.rememberdebtscode.model.enums.ERole;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByNombreRol(ERole nombreRol);
}
