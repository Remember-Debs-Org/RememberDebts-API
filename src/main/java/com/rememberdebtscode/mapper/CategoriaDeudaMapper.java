package com.rememberdebtscode.mapper;

import com.rememberdebtscode.dto.CategoriaDeudaRequestDTO;
import com.rememberdebtscode.dto.CategoriaDeudaResponseDTO;
import com.rememberdebtscode.model.entity.CategoriaDeuda;
import org.springframework.stereotype.Component;

@Component
public class CategoriaDeudaMapper {

    // Convierte Request DTO a entidad (para crear o actualizar)
    public CategoriaDeuda toEntity(CategoriaDeudaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        CategoriaDeuda categoria = new CategoriaDeuda();
        categoria.setNombre(dto.getNombre());
        return categoria;
    }

    // Convierte entidad a Response DTO (para enviar datos al cliente)
    public CategoriaDeudaResponseDTO toDto(CategoriaDeuda categoria) {
        if (categoria == null) {
            return null;
        }
        CategoriaDeudaResponseDTO dto = new CategoriaDeudaResponseDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        return dto;
    }

    // Actualiza entidad existente con datos del Request DTO (para update)
    public void updateEntityFromDto(CategoriaDeudaRequestDTO dto, CategoriaDeuda categoria) {
        if (dto == null || categoria == null) {
            return;
        }
        categoria.setNombre(dto.getNombre());
    }
}