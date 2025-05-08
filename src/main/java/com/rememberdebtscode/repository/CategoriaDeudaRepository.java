package com.rememberdebtscode.repository;

import com.rememberdebtscode.model.entity.CategoriaDeuda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaDeudaRepository extends JpaRepository<CategoriaDeuda, Integer> {
    Optional<CategoriaDeuda> findByNombre(String nombre);
}