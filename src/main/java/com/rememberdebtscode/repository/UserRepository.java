package com.rememberdebtscode.repository;

import com.rememberdebtscode.model.entity.User;
import com.rememberdebtscode.model.enums.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByEstado(EstadoUsuario estado);
}
