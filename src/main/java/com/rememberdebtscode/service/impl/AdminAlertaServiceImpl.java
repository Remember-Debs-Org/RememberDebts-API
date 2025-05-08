package com.rememberdebtscode.service.impl;

import com.rememberdebtscode.dto.AlertaRequestDTO;
import com.rememberdebtscode.dto.AlertaResponseDTO;
import com.rememberdebtscode.mapper.AlertaMapper;
import com.rememberdebtscode.model.entity.Alerta;
import com.rememberdebtscode.repository.AlertaRepository;
import com.rememberdebtscode.service.AdminAlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminAlertaServiceImpl implements AdminAlertaService {

    private final AlertaRepository alertaRepository;
    private final AlertaMapper alertaMapper;

    @Transactional(readOnly = true)
    @Override
    public List<AlertaResponseDTO> getAll() {
        return alertaRepository.findAll().stream()
                .map(alertaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public AlertaResponseDTO findById(Integer id) {
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));
        return alertaMapper.toDto(alerta);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AlertaResponseDTO> findByDeudaId(Integer deudaId) {
        return alertaRepository.findByDeudaId(deudaId).stream()
                .map(alertaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AlertaResponseDTO> findPendingAlerts() {
        return alertaRepository.findByEnviadaFalse().stream()
                .map(alertaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public AlertaResponseDTO create(AlertaRequestDTO dto) {
        Alerta alerta = alertaMapper.toEntity(dto);
        Alerta saved = alertaRepository.save(alerta);
        return alertaMapper.toDto(saved);
    }

    @Transactional
    @Override
    public AlertaResponseDTO update(Integer id, AlertaRequestDTO dto) {
        Alerta alertaExistente = alertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada con id: " + id));

        alertaExistente.setFechaAlerta(dto.getFechaAlerta());
        alertaExistente.setTipo(dto.getTipo());
        alertaExistente.setMensaje(dto.getMensaje());
        alertaExistente.setEnviada(dto.getEnviada() != null ? dto.getEnviada() : false);
        // No actualices la deuda para evitar inconsistencias

        Alerta updated = alertaRepository.save(alertaExistente);
        return alertaMapper.toDto(updated);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Alerta alertaExistente = alertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada con id: " + id));
        alertaRepository.delete(alertaExistente);
    }
}