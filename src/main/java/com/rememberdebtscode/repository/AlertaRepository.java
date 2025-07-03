package com.rememberdebtscode.repository;

import com.rememberdebtscode.model.entity.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Integer> {
    List<Alerta> findByDeudaId(Integer deudaId);

    List<Alerta> findByEnviadaFalse();
}