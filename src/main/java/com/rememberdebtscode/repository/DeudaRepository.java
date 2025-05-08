package com.rememberdebtscode.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rememberdebtscode.model.entity.Deuda;

public interface DeudaRepository extends JpaRepository<Deuda, Integer> {
    List<Deuda> findByCategoriaId(Integer categoriaId);

    List<Deuda> findByCategoriaNombre(String categoriaNombre);

    List<Deuda> findByUserId(Integer userId);
}
