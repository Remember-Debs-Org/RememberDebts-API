package com.rememberdebtscode.service.impl;

import com.rememberdebtscode.dto.DeudaRequestDTO;
import com.rememberdebtscode.dto.DeudaResponseDTO;
import com.rememberdebtscode.mapper.DeudaMapper;
import com.rememberdebtscode.model.entity.CategoriaDeuda;
import com.rememberdebtscode.model.entity.Deuda;
import com.rememberdebtscode.model.entity.Usuario;
import com.rememberdebtscode.repository.CategoriaDeudaRepository;
import com.rememberdebtscode.repository.DeudaRepository;
import com.rememberdebtscode.repository.UsuarioRepository;
import com.rememberdebtscode.service.AdminDeudaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminDeudaServiceImpl implements AdminDeudaService {

    private final DeudaRepository deudaRepository;
    private final DeudaMapper deudaMapper;
    private final CategoriaDeudaRepository categoriaDeudaRepository;
    private final UsuarioRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<DeudaResponseDTO> getAll() {
        List<Deuda> deudas = deudaRepository.findAll();
        return deudas.stream()
                .map(deudaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeudaResponseDTO> findByCategoriaId(Integer categoriaId) {
        List<Deuda> deudas = deudaRepository.findByCategoriaId(categoriaId);
        return deudas.stream()
                .map(deudaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<DeudaResponseDTO> findByCategoriaNombre(String categoriaNombre) {
        List<Deuda> deudas = deudaRepository.findByCategoriaNombre(categoriaNombre);
        return deudas.stream()
                .map(deudaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public DeudaResponseDTO findById(Integer id) {
        Deuda deuda = deudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada con id: " + id));
        return deudaMapper.toDto(deuda);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DeudaResponseDTO> findByUserId(Integer userId) {
        List<Deuda> deudas = deudaRepository.findByUserId(userId);
        return deudas.stream()
                .map(deudaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public DeudaResponseDTO create(DeudaRequestDTO dto) {
        if (dto.getFechaVencimiento() != null && dto.getFechaVencimiento().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede ser anterior a hoy");
        }

        // 1. Buscar la categoría
        CategoriaDeuda categoria = categoriaDeudaRepository.findByNombre(dto.getCategoriaNombre())
                .orElseThrow(
                        () -> new RuntimeException("Categoría no encontrada con nombre: " + dto.getCategoriaNombre()));

        // 2. Buscar el usuario
        Usuario user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getUserId()));

        // 3. Convertir DTO a entidad
        Deuda deuda = deudaMapper.toEntity(dto);

        // 4. Asignar la categoría y el usuario
        deuda.setCategoria(categoria);
        deuda.setUser(user);

        // 5. Guardar y devolver
        Deuda saved = deudaRepository.save(deuda);
        return deudaMapper.toDto(saved);
    }

    @Transactional
    @Override
    public DeudaResponseDTO update(Integer id, DeudaRequestDTO dto) {
        Deuda deudaExistente = deudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada con id: " + id));

        // Validar fecha de vencimiento
        if (dto.getFechaVencimiento() != null && dto.getFechaVencimiento().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede ser anterior a hoy");
        }

        deudaExistente.setNombre(dto.getNombre());
        deudaExistente.setDescripcion(dto.getDescripcion());
        deudaExistente.setMonto(dto.getMonto());
        deudaExistente.setFechaVencimiento(dto.getFechaVencimiento());
        deudaExistente.setEstado(dto.getEstado());
        deudaExistente.setRecurrente(dto.getRecurrente());
        deudaExistente.setFrecuencia(dto.getFrecuencia());

        // Validar y asignar categoría obligatoria por nombre
        CategoriaDeuda categoria = categoriaDeudaRepository.findByNombre(dto.getCategoriaNombre())
                .orElseThrow(
                        () -> new RuntimeException("Categoría no encontrada con nombre: " + dto.getCategoriaNombre()));
        deudaExistente.setCategoria(categoria);

        Deuda updated = deudaRepository.save(deudaExistente);
        return deudaMapper.toDto(updated);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Deuda deudaExistente = deudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada con id: " + id));
        deudaRepository.delete(deudaExistente);
    }
}
