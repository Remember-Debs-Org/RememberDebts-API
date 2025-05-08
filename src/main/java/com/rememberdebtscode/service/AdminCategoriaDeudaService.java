package com.rememberdebtscode.service;

import com.rememberdebtscode.dto.CategoriaDeudaRequestDTO;
import com.rememberdebtscode.dto.CategoriaDeudaResponseDTO;

import java.util.List;
import java.util.Optional;

public interface AdminCategoriaDeudaService {

    List<CategoriaDeudaResponseDTO> getAll();

    Optional<CategoriaDeudaResponseDTO> findById(Integer id);

    Optional<CategoriaDeudaResponseDTO> findByNombre(String nombre);

    CategoriaDeudaResponseDTO create(CategoriaDeudaRequestDTO dto);

    CategoriaDeudaResponseDTO update(Integer id, CategoriaDeudaRequestDTO dto);

    void delete(Integer id);
}