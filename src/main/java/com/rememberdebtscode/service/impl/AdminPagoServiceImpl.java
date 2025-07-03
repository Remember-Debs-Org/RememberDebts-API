package com.rememberdebtscode.service.impl;

import com.rememberdebtscode.dto.PagoRequestDTO;
import com.rememberdebtscode.dto.PagoResponseDTO;
import com.rememberdebtscode.mapper.PagoMapper;
import com.rememberdebtscode.model.entity.Pago;
import com.rememberdebtscode.repository.DeudaRepository;
import com.rememberdebtscode.repository.PagoRepository;
import com.rememberdebtscode.service.AdminPagoService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminPagoServiceImpl implements AdminPagoService {

    private final PagoRepository pagoRepository;
    private final DeudaRepository deudaRepository;
    private final PagoMapper pagoMapper;

    @Transactional(readOnly = true)
    @Override
    public List<PagoResponseDTO> getAll() {
        return pagoRepository.findAll()
                .stream()
                .map(pagoMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public PagoResponseDTO findById(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado"));
        return pagoMapper.toDto(pago);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PagoResponseDTO> findByDeudaId(Integer deudaId) {
        return pagoRepository.findByDeudaId(deudaId)
                .stream()
                .map(pagoMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public PagoResponseDTO create(PagoRequestDTO dto, MultipartFile comprobante) {
        // 1. Obtener la deuda
        var deuda = deudaRepository.findById(dto.getDeudaId())
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada con id: " + dto.getDeudaId()));

        // 2. Validaciones
        if (dto.getFechaPago().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de pago no puede ser futura");
        }

        // 3. ACTUALIZA LA DEUDA
        deuda.setEstado(com.rememberdebtscode.model.enums.EstadoDeuda.PAGADA);
        deuda.setFechaPago(dto.getFechaPago());

        // Si la deuda es recurrente: 
        // - se deja en PAGADA, se actualiza fechaPago
        // - el ciclo siguiente se renueva solo cuando llegue el primer día del nuevo periodo (ver paso 2 abajo)
        // Así que NO cambias fechaLimitePago ni el estado aquí.

        deudaRepository.save(deuda);

        // 4. Crea la entidad pago desde DTO
        Pago pago = pagoMapper.toEntity(dto);
        pago.setDeuda(deuda);

        // 5. Procesar comprobante
        if (comprobante != null && !comprobante.isEmpty()) {
            try {
                String carpeta = "uploads/comprobantes/";
                String nombreArchivo = System.currentTimeMillis() + "_" + comprobante.getOriginalFilename();
                java.nio.file.Path ruta = java.nio.file.Paths.get(carpeta, nombreArchivo);
                java.nio.file.Files.createDirectories(ruta.getParent());
                comprobante.transferTo(ruta);
                pago.setComprobanteUrl(nombreArchivo);
            } catch (Exception ex) {
                throw new RuntimeException("Error al guardar el comprobante: " + ex.getMessage(), ex);
            }
        }

        Pago saved = pagoRepository.save(pago);
        return pagoMapper.toDto(saved);
    }

    @Transactional
    @Override
    public PagoResponseDTO update(Integer id, PagoRequestDTO dto) {
        Pago pagoExistente = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con id: " + id));

        // Validaciones
        if (dto.getFechaPago().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de pago no puede ser futura");
        }

        pagoExistente.setFechaPago(dto.getFechaPago());
        pagoExistente.setMontoPagado(dto.getMontoPagado());
        pagoExistente.setNotas(dto.getNotas());

        Pago updated = pagoRepository.save(pagoExistente);
        return pagoMapper.toDto(updated);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Pago pagoExistente = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con id: " + id));
        pagoRepository.delete(pagoExistente);
    }
}