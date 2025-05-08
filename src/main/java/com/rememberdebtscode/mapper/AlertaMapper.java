package com.rememberdebtscode.mapper;

import com.rememberdebtscode.dto.AlertaRequestDTO;
import com.rememberdebtscode.dto.AlertaResponseDTO;
import com.rememberdebtscode.model.entity.Alerta;
import com.rememberdebtscode.model.entity.Deuda;
import com.rememberdebtscode.repository.DeudaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class AlertaMapper {

    private final DeudaRepository deudaRepository;

    public Alerta toEntity(AlertaRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Deuda deuda = deudaRepository.findById(dto.getDeudaId())
                .orElseThrow(() -> new NoSuchElementException("Deuda no encontrada con id: " + dto.getDeudaId()));

        Alerta alerta = new Alerta();
        alerta.setDeuda(deuda);
        alerta.setFechaAlerta(dto.getFechaAlerta());
        alerta.setTipo(dto.getTipo());
        alerta.setEnviada(dto.getEnviada() != null ? dto.getEnviada() : false);
        alerta.setMensaje(dto.getMensaje());

        return alerta;
    }

    // Convierte Alerta a AlertaResponseDTO (para enviar datos al cliente)
    public AlertaResponseDTO toDto(Alerta alerta) {
        if (alerta == null) {
            return null;
        }

        AlertaResponseDTO dto = new AlertaResponseDTO();
        dto.setId(alerta.getId());
        dto.setDeudaId(alerta.getDeuda().getId());
        dto.setFechaAlerta(alerta.getFechaAlerta());
        dto.setTipo(alerta.getTipo());
        dto.setEnviada(alerta.getEnviada());
        dto.setMensaje(alerta.getMensaje());

        return dto;
    }
}