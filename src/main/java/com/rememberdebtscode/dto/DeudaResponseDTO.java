package com.rememberdebtscode.dto;

import com.rememberdebtscode.model.enums.EstadoDeuda;
import com.rememberdebtscode.model.enums.FrecuenciaDeuda;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DeudaResponseDTO {
    private Integer id;
    private Integer userId;
    private String nombre;
    private String categoriaNombre;
    private String descripcion;
    private Double monto;
    private LocalDate fechaVencimiento;
    private EstadoDeuda estado;
    private Boolean recurrente;
    private FrecuenciaDeuda frecuencia;
    private LocalDate fechaCreacion;
}
