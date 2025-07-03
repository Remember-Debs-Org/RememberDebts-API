package com.rememberdebtscode.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @Email(message = "El correo elecrónico no es válido")
    @NotBlank(message = "El correo electrónico es obligattorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
