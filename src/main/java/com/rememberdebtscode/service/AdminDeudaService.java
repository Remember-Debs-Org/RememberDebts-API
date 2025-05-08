package com.rememberdebtscode.service;

import com.rememberdebtscode.dto.DeudaRequestDTO;
import com.rememberdebtscode.dto.DeudaResponseDTO;

import java.util.List;

public interface AdminDeudaService {
    List<DeudaResponseDTO> getAll();

    List<DeudaResponseDTO> findByCategoriaId(Integer categoriaId);

    // Nuevo método para buscar por nombre de categoría
    List<DeudaResponseDTO> findByCategoriaNombre(String categoriaNombre);

    DeudaResponseDTO findById(Integer id);

    List<DeudaResponseDTO> findByUserId(Integer userId);

    DeudaResponseDTO create(DeudaRequestDTO dto);

    DeudaResponseDTO update(Integer id, DeudaRequestDTO dto);

    void delete(Integer id);
}
