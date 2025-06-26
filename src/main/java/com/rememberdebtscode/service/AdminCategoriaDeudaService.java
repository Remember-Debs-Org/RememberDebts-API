package com.rememberdebtscode.service;

import com.rememberdebtscode.dto.CategoriaDeudaRequestDTO;
import com.rememberdebtscode.dto.CategoriaDeudaResponseDTO;
import com.rememberdebtscode.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface AdminCategoriaDeudaService {

    List<CategoriaDeudaResponseDTO> getAll(User user); // ← solo de ese usuario
    Optional<CategoriaDeudaResponseDTO> findById(Integer id, User user);
    Optional<CategoriaDeudaResponseDTO> findByNombre(String nombre, User user);
    CategoriaDeudaResponseDTO create(CategoriaDeudaRequestDTO dto, User user);
    CategoriaDeudaResponseDTO update(Integer id, CategoriaDeudaRequestDTO dto, User user);
    void delete(Integer id, User user);

}