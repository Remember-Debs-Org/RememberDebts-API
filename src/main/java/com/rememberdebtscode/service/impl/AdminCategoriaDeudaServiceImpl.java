package com.rememberdebtscode.service.impl;

import com.rememberdebtscode.dto.CategoriaDeudaRequestDTO;
import com.rememberdebtscode.dto.CategoriaDeudaResponseDTO;
import com.rememberdebtscode.mapper.CategoriaDeudaMapper;
import com.rememberdebtscode.model.entity.CategoriaDeuda;
import com.rememberdebtscode.repository.CategoriaDeudaRepository;
import com.rememberdebtscode.service.AdminCategoriaDeudaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminCategoriaDeudaServiceImpl implements AdminCategoriaDeudaService {

    private final CategoriaDeudaRepository categoriaDeudaRepository;
    private final CategoriaDeudaMapper categoriaDeudaMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CategoriaDeudaResponseDTO> getAll() {
        List<CategoriaDeuda> categorias = categoriaDeudaRepository.findAll();
        return categorias.stream()
                .map(categoriaDeudaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CategoriaDeudaResponseDTO> findById(Integer id) {
        return categoriaDeudaRepository.findById(id)
                .map(categoriaDeudaMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CategoriaDeudaResponseDTO> findByNombre(String nombre) {
        return categoriaDeudaRepository.findByNombre(nombre)
                .map(categoriaDeudaMapper::toDto);
    }

    @Transactional
    @Override
    public CategoriaDeudaResponseDTO create(CategoriaDeudaRequestDTO dto) {
        // Validar que no exista una categoría con el mismo nombre
        if (categoriaDeudaRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + dto.getNombre());
        }

        CategoriaDeuda categoria = categoriaDeudaMapper.toEntity(dto);
        CategoriaDeuda saved = categoriaDeudaRepository.save(categoria);
        return categoriaDeudaMapper.toDto(saved);
    }

    @Transactional
    @Override
    public CategoriaDeudaResponseDTO update(Integer id, CategoriaDeudaRequestDTO dto) {
        CategoriaDeuda categoriaExistente = categoriaDeudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));

        // Validar que no exista otra categoría con el mismo nombre (distinto id)
        categoriaDeudaRepository.findByNombre(dto.getNombre())
                .filter(c -> !c.getId().equals(id))
                .ifPresent(c -> {
                    throw new IllegalArgumentException("Ya existe otra categoría con el nombre: " + dto.getNombre());
                });

        categoriaExistente.setNombre(dto.getNombre());
        categoriaExistente.setImagenUrl(dto.getImagenUrl());

        CategoriaDeuda updated = categoriaDeudaRepository.save(categoriaExistente);
        return categoriaDeudaMapper.toDto(updated);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        CategoriaDeuda categoriaExistente = categoriaDeudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
        categoriaDeudaRepository.delete(categoriaExistente);
    }
}
