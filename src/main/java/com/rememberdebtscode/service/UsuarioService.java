package com.rememberdebtscode.service;

import com.rememberdebtscode.dto.UserResponseDTO;
import com.rememberdebtscode.model.enums.EstadoUsuario;

import java.util.List;

public interface UsuarioService {
    List<UserResponseDTO> getAll();

    List<UserResponseDTO> getByEstado(EstadoUsuario estado);

    UserResponseDTO findById(Integer id);

    UserResponseDTO suspend(Integer id);

    UserResponseDTO activate(Integer id);

    String delete(Integer id);
}
