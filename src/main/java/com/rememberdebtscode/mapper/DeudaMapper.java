package com.rememberdebtscode.mapper;

import com.rememberdebtscode.dto.DeudaRequestDTO;
import com.rememberdebtscode.dto.DeudaResponseDTO;
import com.rememberdebtscode.model.entity.Deuda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeudaMapper {

    public Deuda toEntity(DeudaRequestDTO dto) {
        if (dto == null) return null;

        Deuda deuda = new Deuda();
        deuda.setNombre(dto.getNombre());
        deuda.setDescripcion(dto.getDescripcion());
        deuda.setMonto(dto.getMonto());
        deuda.setEstado(dto.getEstado());
        deuda.setRecurrente(dto.getRecurrente());
        deuda.setFrecuencia(dto.getFrecuencia());

        deuda.setFechaLimitePago(dto.getFechaLimitePago());
        deuda.setFechaPago(dto.getFechaPago());
        deuda.setFechaVencimiento(dto.getFechaVencimiento());

        return deuda;
    }

    public DeudaResponseDTO toDto(Deuda deuda) {
        if (deuda == null) return null;

        DeudaResponseDTO dto = new DeudaResponseDTO();
        dto.setId(deuda.getId());
        dto.setUserId(deuda.getUser() != null ? deuda.getUser().getId() : null);
        dto.setNombre(deuda.getNombre());
        dto.setCategoriaNombre(deuda.getCategoria() != null ? deuda.getCategoria().getNombre() : null);
        dto.setDescripcion(deuda.getDescripcion());
        dto.setMonto(deuda.getMonto());
        dto.setEstado(deuda.getEstado());
        dto.setRecurrente(deuda.getRecurrente());
        dto.setFrecuencia(deuda.getFrecuencia());

        dto.setFechaLimitePago(deuda.getFechaLimitePago());
        dto.setFechaPago(deuda.getFechaPago());
        dto.setFechaVencimiento(deuda.getFechaVencimiento());

        dto.setFechaCreacion(deuda.getFechaCreacion());

        return dto;
    }
}
