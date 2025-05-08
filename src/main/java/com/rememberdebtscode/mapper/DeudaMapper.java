package com.rememberdebtscode.mapper;

import com.rememberdebtscode.dto.DeudaRequestDTO;
import com.rememberdebtscode.dto.DeudaResponseDTO;
import com.rememberdebtscode.model.entity.Deuda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeudaMapper {

    /**
     * Convierte DeudaRequestDTO a entidad Deuda.
     * No asigna usuario ni categoría, eso debe hacerlo el servicio.
     */
    public Deuda toEntity(DeudaRequestDTO dto) {
        if (dto == null)
            return null;

        Deuda deuda = new Deuda();
        deuda.setNombre(dto.getNombre());
        deuda.setDescripcion(dto.getDescripcion());
        deuda.setMonto(dto.getMonto());
        deuda.setFechaVencimiento(dto.getFechaVencimiento());
        deuda.setEstado(dto.getEstado());
        deuda.setRecurrente(dto.getRecurrente());
        deuda.setFrecuencia(dto.getFrecuencia());

        return deuda;
    }

    /**
     * Convierte entidad Deuda a DeudaResponseDTO.
     * Asigna nombre de categoría y usuario.
     */
    public DeudaResponseDTO toDto(Deuda deuda) {
        if (deuda == null)
            return null;

        DeudaResponseDTO dto = new DeudaResponseDTO();
        dto.setId(deuda.getId());
        dto.setUserId(deuda.getUser() != null ? deuda.getUser().getId() : null);
        dto.setNombre(deuda.getNombre());
        dto.setCategoriaNombre(deuda.getCategoria() != null ? deuda.getCategoria().getNombre() : null);
        dto.setDescripcion(deuda.getDescripcion());
        dto.setMonto(deuda.getMonto());
        dto.setFechaVencimiento(deuda.getFechaVencimiento());
        dto.setEstado(deuda.getEstado());
        dto.setRecurrente(deuda.getRecurrente());
        dto.setFrecuencia(deuda.getFrecuencia());
        dto.setFechaCreacion(deuda.getFechaCreacion());

        return dto;
    }
}
