package com.rememberdebtscode.mapper;

import com.rememberdebtscode.dto.PagoRequestDTO;
import com.rememberdebtscode.dto.PagoResponseDTO;
import com.rememberdebtscode.model.entity.Pago;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PagoMapper {

    public Pago toEntity(PagoRequestDTO dto) {
        if (dto == null)
            return null;

        Pago pago = new Pago();
        pago.setFechaPago(dto.getFechaPago());
        pago.setMontoPagado(dto.getMontoPagado());
        pago.setNotas(dto.getNotas());
        // NO se asigna el comprobante aquí (es gestionado en el service al guardar archivo)
        return pago;
    }

    public PagoResponseDTO toDto(Pago pago) {
        if (pago == null)
            return null;

        PagoResponseDTO dto = new PagoResponseDTO();
        dto.setId(pago.getId());
        dto.setDeudaId(pago.getDeuda() != null ? pago.getDeuda().getId() : null);
        dto.setFechaPago(pago.getFechaPago());
        dto.setMontoPagado(pago.getMontoPagado());
        dto.setNotas(pago.getNotas());
        dto.setFechaRegistro(pago.getFechaRegistro());
        dto.setComprobanteUrl(pago.getComprobanteUrl()); // NUEVO: añadir comprobante
        return dto;
    }
}
