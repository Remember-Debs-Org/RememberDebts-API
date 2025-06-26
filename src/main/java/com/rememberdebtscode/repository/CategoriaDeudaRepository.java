package com.rememberdebtscode.repository;

import com.rememberdebtscode.model.entity.CategoriaDeuda;
import com.rememberdebtscode.model.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaDeudaRepository extends JpaRepository<CategoriaDeuda, Integer> {
    Optional<CategoriaDeuda> findByNombre(String nombre);
    List<CategoriaDeuda> findByUser(User user);
    Optional<CategoriaDeuda> findByNombreAndUser(String nombre, User user);
}