package com.rememberdebtscode.service;

import com.rememberdebtscode.dto.AlertaRequestDTO;
import com.rememberdebtscode.dto.AlertaResponseDTO;

import java.util.List;

public interface AdminAlertaService {
    List<AlertaResponseDTO> getAll();

    AlertaResponseDTO findById(Integer id);

    List<AlertaResponseDTO> findByDeudaId(Integer deudaId);

    List<AlertaResponseDTO> findPendingAlerts();

    AlertaResponseDTO create(AlertaRequestDTO dto);

    AlertaResponseDTO update(Integer id, AlertaRequestDTO dto);

    void delete(Integer id);
}
