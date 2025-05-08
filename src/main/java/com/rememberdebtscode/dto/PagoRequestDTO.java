package com.rememberdebtscode.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PagoRequestDTO {

    @NotNull(message = "El ID de la deuda es obligatorio")
    private Integer deudaId;

    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDate fechaPago;

    @NotNull(message = "El monto pagado es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto pagado debe ser mayor a 0")
    private Double montoPagado;

    @Size(max = 500, message = "Las notas deben tener máximo 500 caracteres")
    private String notas;
}