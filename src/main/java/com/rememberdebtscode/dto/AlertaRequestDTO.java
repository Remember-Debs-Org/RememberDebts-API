package com.rememberdebtscode.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AlertaRequestDTO {

    @NotNull(message = "El ID de la deuda es obligatorio")
    private Integer deudaId;

    @NotNull(message = "La fecha de alerta es obligatoria")
    private LocalDate fechaAlerta;

    @NotBlank(message = "El tipo de alerta es obligatorio")
    @Size(max = 100, message = "El tipo de alerta debe tener máximo 100 caracteres")
    private String tipo;

    private Boolean enviada = false;

    @Size(max = 500, message = "El mensaje debe tener máximo 500 caracteres")
    private String mensaje;
}
