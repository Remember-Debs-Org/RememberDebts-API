package com.rememberdebtscode.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rememberdebtscode.model.entity.Deuda;
import com.rememberdebtscode.model.enums.EstadoDeuda;

public interface DeudaRepository extends JpaRepository<Deuda, Integer> {
    List<Deuda> findByCategoriaId(Integer categoriaId);

    List<Deuda> findByCategoriaNombre(String categoriaNombre);

    List<Deuda> findByUserId(Integer userId);

    List<Deuda> findByEstado(EstadoDeuda estado);

    // --- NUEVO MÉTODO PARA RENOVAR CICLOS RECURRENTES ---
    List<Deuda> findByEstadoAndRecurrente(EstadoDeuda estado, Boolean recurrente);
}
