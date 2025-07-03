package com.rememberdebtscode.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private String firstName;
    private String lastName;
    private String role;
}