package com.rememberdebtscode.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse {
    private LocalDateTime dateTime;
    private String message;
    private String details;
}
