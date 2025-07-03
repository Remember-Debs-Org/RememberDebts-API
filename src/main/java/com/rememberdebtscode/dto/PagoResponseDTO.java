package com.rememberdebtscode.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PagoResponseDTO {
    private Integer id;
    private Integer deudaId;
    private LocalDate fechaPago;
    private Double montoPagado;
    private String notas;
    private String comprobanteUrl;
    private LocalDate fechaRegistro;
}
