package com.rememberdebtscode.service.impl;

import com.rememberdebtscode.dto.UserResponseDTO;
import com.rememberdebtscode.mapper.UserMapper;
import com.rememberdebtscode.model.entity.Usuario;
import com.rememberdebtscode.model.enums.EstadoUsuario;
import com.rememberdebtscode.repository.UsuarioRepository;
import com.rememberdebtscode.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDTO> getAll() {
        List<Usuario> users = userRepository.findByEstado(EstadoUsuario.ACTIVO);
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDTO> getByEstado(EstadoUsuario estado) {
        List<Usuario> users = userRepository.findByEstado(estado);
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponseDTO findById(Integer id) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public UserResponseDTO suspend(Integer id) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        if (user.getEstado() == EstadoUsuario.SUSPENDIDO) {
            throw new IllegalStateException("El usuario ya está suspendido");
        }
        if (user.getEstado() == EstadoUsuario.ELIMINADO) {
            throw new IllegalStateException("No se puede suspender un usuario eliminado");
        }

        user.setEstado(EstadoUsuario.SUSPENDIDO);
        Usuario updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Transactional
    @Override
    public UserResponseDTO activate(Integer id) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        if (user.getEstado() == EstadoUsuario.ACTIVO) {
            throw new IllegalStateException("El usuario ya está activo");
        }

        user.setEstado(EstadoUsuario.ACTIVO);
        Usuario updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Transactional
    @Override
    public String delete(Integer id) {
        Usuario user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        if (user.getEstado() == EstadoUsuario.ELIMINADO) {
            throw new IllegalStateException("El usuario ya está eliminado");
        }

        user.setEstado(EstadoUsuario.ELIMINADO);
        user.setDeletedAt(LocalDate.now());
        userRepository.save(user);

        return "Usuario con ID " + id + " eliminado correctamente.";
    }
}
