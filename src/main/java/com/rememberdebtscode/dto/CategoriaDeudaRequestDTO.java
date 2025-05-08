package com.rememberdebtscode.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoriaDeudaRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre debe tener máximo 100 caracteres")
    private String nombre;

    @Size(max = 255, message = "La URL de la imagen debe tener máximo 255 caracteres")
    private String imagenUrl;
}