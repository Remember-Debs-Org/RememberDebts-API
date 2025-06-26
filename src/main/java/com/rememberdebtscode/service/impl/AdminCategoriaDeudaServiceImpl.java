package com.rememberdebtscode.service.impl;

import com.rememberdebtscode.dto.CategoriaDeudaRequestDTO;
import com.rememberdebtscode.dto.CategoriaDeudaResponseDTO;
import com.rememberdebtscode.mapper.CategoriaDeudaMapper;
import com.rememberdebtscode.model.entity.CategoriaDeuda;
import com.rememberdebtscode.model.entity.User;
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
    public List<CategoriaDeudaResponseDTO> getAll(User user) {
        List<CategoriaDeuda> categorias = categoriaDeudaRepository.findByUser(user);
        return categorias.stream()
                .map(categoriaDeudaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CategoriaDeudaResponseDTO> findById(Integer id, User user) {
        return categoriaDeudaRepository.findById(id)
            .filter(cat -> cat.getUser().getId().equals(user.getId()))
            .map(categoriaDeudaMapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<CategoriaDeudaResponseDTO> findByNombre(String nombre, User user) {
        return categoriaDeudaRepository.findByNombreAndUser(nombre, user)
            .map(categoriaDeudaMapper::toDto);
    }

    @Transactional
    @Override
    public CategoriaDeudaResponseDTO create(CategoriaDeudaRequestDTO dto, User user) {
        // Validar que no exista una categoría con ese nombre para este usuario
        if (categoriaDeudaRepository.findByNombreAndUser(dto.getNombre(), user).isPresent()) {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + dto.getNombre());
        }

        CategoriaDeuda categoria = categoriaDeudaMapper.toEntity(dto);
        categoria.setUser(user); // ← ASOCIA USUARIO AQUÍ
        CategoriaDeuda saved = categoriaDeudaRepository.save(categoria);
        return categoriaDeudaMapper.toDto(saved);
    }


    @Transactional
    @Override
    public CategoriaDeudaResponseDTO update(Integer id, CategoriaDeudaRequestDTO dto, User user) {
        CategoriaDeuda categoriaExistente = categoriaDeudaRepository.findById(id)
                .filter(cat -> cat.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada o no autorizada con id: " + id));

        // Validar nombre duplicado solo para este usuario
        categoriaDeudaRepository.findByNombreAndUser(dto.getNombre(), user)
            .filter(c -> !c.getId().equals(id))
            .ifPresent(c -> {
                throw new IllegalArgumentException("Ya existe otra categoría con el nombre: " + dto.getNombre());
            });

        categoriaExistente.setNombre(dto.getNombre());
        CategoriaDeuda updated = categoriaDeudaRepository.save(categoriaExistente);
        return categoriaDeudaMapper.toDto(updated);
    }

    @Transactional
    @Override
    public void delete(Integer id, User user) {
        CategoriaDeuda categoriaExistente = categoriaDeudaRepository.findById(id)
                .filter(cat -> cat.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada o no autorizada con id: " + id));
        categoriaDeudaRepository.delete(categoriaExistente);
    }

}
