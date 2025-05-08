package com.rememberdebtscode.service;

import com.rememberdebtscode.dto.PagoRequestDTO;
import com.rememberdebtscode.dto.PagoResponseDTO;

import java.util.List;

public interface AdminPagoService {
    List<PagoResponseDTO> getAll();

    PagoResponseDTO findById(Integer id);

    List<PagoResponseDTO> findByDeudaId(Integer deudaId);

    PagoResponseDTO create(PagoRequestDTO dto);

    PagoResponseDTO update(Integer id, PagoRequestDTO dto);

    void delete(Integer id);
}