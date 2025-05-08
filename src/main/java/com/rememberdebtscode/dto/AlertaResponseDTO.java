package com.rememberdebtscode.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AlertaResponseDTO {
    private Integer id;
    private Integer deudaId;
    private LocalDate fechaAlerta;
    private String tipo;
    private Boolean enviada;
    private String mensaje;
}
