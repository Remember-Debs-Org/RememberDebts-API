package com.rememberdebtscode.repository;

import com.rememberdebtscode.model.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByDeudaId(Integer deudaId);
}
