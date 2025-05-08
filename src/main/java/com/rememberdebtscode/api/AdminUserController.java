package com.rememberdebtscode.api;

import com.rememberdebtscode.dto.UserResponseDTO;
import com.rememberdebtscode.model.enums.EstadoUsuario;
import com.rememberdebtscode.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UsuarioService adminUserService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = adminUserService.getAll();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<UserResponseDTO>> getUsersByEstado(@PathVariable EstadoUsuario estado) {
        List<UserResponseDTO> users = adminUserService.getByEstado(estado);
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Integer id) {
        UserResponseDTO user = adminUserService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN') or (#id == principal.id)")
    @PatchMapping("/{id}/suspender")
    public ResponseEntity<UserResponseDTO> suspenderUsuario(@PathVariable("id") Integer id) {
        UserResponseDTO user = adminUserService.suspend(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN') or (#id == principal.id)")
    @PatchMapping("/{id}/activar")
    public ResponseEntity<UserResponseDTO> activarUsuario(@PathVariable("id") Integer id) {
        UserResponseDTO user = adminUserService.activate(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN') or (#id == principal.id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) {
        String mensaje = adminUserService.delete(id);
        return ResponseEntity.ok(mensaje);
    }
}
