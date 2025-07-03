package com.rememberdebtscode.service;

import com.rememberdebtscode.dto.PagoRequestDTO;
import com.rememberdebtscode.dto.PagoResponseDTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface AdminPagoService {
    List<PagoResponseDTO> getAll();

    PagoResponseDTO findById(Integer id);

    List<PagoResponseDTO> findByDeudaId(Integer deudaId);

    PagoResponseDTO create(PagoRequestDTO dto, MultipartFile comprobante);

    PagoResponseDTO update(Integer id, PagoRequestDTO dto);

    void delete(Integer id);
}