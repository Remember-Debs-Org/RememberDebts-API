package com.rememberdebtscode.dto;

import com.rememberdebtscode.model.enums.ERole;

import lombok.Data;

@Data
public class UserProfileDTO {
    private Integer id;
    private String email;
    private ERole role;
    private String firstName;
    private String lastName;
    private String Telefono;
}
