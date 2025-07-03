package com.rememberdebtscode.dto;

import com.rememberdebtscode.model.enums.EstadoUsuario;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String userEmail;
    private String userTelefono;
    private EstadoUsuario estado;
    private LocalDate createAt;
    private LocalDate updateAt;
}
