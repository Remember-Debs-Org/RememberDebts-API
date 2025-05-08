package com.rememberdebtscode.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre debe tener 50 caracteres o menos")
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 50, message = "El nombre debe tener 50 caracteres o menos")
    private String lastName;

    @NotBlank(message = "La telefono es obligatoria")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "El teléfono debe contener solo números y puede incluir un prefijo '+' (7 a 15 dígitos)")
    private String userTelefono;

    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email es obligatorio")
    private String userEmail;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, max = 20, message = "La contraseña debe tener entre 8 y 20 caracteres")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=]).*$", message = "La contraseña debe contener al menos un número, una letra minúscula, una letra mayúscula y un carácter especial")
    private String userPassword;
}
