package com.rememberdebtscode.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rememberdebtscode.dto.AuthResponseDTO;
import com.rememberdebtscode.dto.LoginDTO;
import com.rememberdebtscode.dto.UserProfileDTO;
import com.rememberdebtscode.dto.UserRegistrationDTO;
import com.rememberdebtscode.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register/usuario")
    public ResponseEntity<UserProfileDTO> registerUsuario(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserProfileDTO userProfile = userService.registerUsario(userRegistrationDTO);
        return new ResponseEntity<>(userProfile, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        AuthResponseDTO authResponse = userService.login(loginDTO);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
