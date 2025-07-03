package com.rememberdebtscode.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rememberdebtscode.model.enums.EstadoDeuda;
import com.rememberdebtscode.model.enums.FrecuenciaDeuda;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeudaRequestDTO {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer userId;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre debe tener máximo 100 caracteres")
    private String nombre;

    @NotNull(message = "El nombre de la categoría es obligatorio")
    private String categoriaNombre;

    @Size(max = 255, message = "La descripción debe tener máximo 255 caracteres")
    private String descripcion;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private Double monto;

    @NotNull(message = "El estado es obligatorio")
    private EstadoDeuda estado;

    @NotNull(message = "El campo recurrente es obligatorio")
    private Boolean recurrente;

    private FrecuenciaDeuda frecuencia;

    // Fechas condicionales
    private LocalDate fechaLimitePago;      // Solo para pendiente
    private LocalDate fechaPago;            // Solo para pagada
    private LocalDate fechaVencimiento;     // Solo para vencida
}
