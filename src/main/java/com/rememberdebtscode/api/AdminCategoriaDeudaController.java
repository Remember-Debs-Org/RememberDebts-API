package com.rememberdebtscode.api;

import com.rememberdebtscode.dto.CategoriaDeudaRequestDTO;
import com.rememberdebtscode.dto.CategoriaDeudaResponseDTO;
import com.rememberdebtscode.model.entity.User;
import com.rememberdebtscode.repository.UserRepository;
import com.rememberdebtscode.service.AdminCategoriaDeudaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/categorias-deuda")
@PreAuthorize("hasAnyRole('USUARIO', 'ADMIN')")
public class AdminCategoriaDeudaController {

    private final AdminCategoriaDeudaService adminCategoriaDeudaService;
    private final UserRepository userRepository;

    // Utilidad para extraer el usuario autenticado desde el Principal
    private User getAuthenticatedUser(Principal principal) {
        // Ajusta este método si tu sistema usa otra forma de obtener el usuario (por ejemplo, por ID en el token)
        return userRepository.findByUserEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Obtener todas las categorías del usuario autenticado
    @GetMapping
    public ResponseEntity<List<CategoriaDeudaResponseDTO>> getAllCategorias(Principal principal) {
        User user = getAuthenticatedUser(principal);
        List<CategoriaDeudaResponseDTO> categorias = adminCategoriaDeudaService.getAll(user);
        return ResponseEntity.ok(categorias);
    }

    // Obtener categoría por ID, si es del usuario autenticado
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDeudaResponseDTO> getCategoriaById(@PathVariable Integer id, Principal principal) {
        User user = getAuthenticatedUser(principal);
        return adminCategoriaDeudaService.findById(id, user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener categoría por nombre (solo si la tiene el usuario autenticado)
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CategoriaDeudaResponseDTO> getCategoriaByNombre(@PathVariable String nombre, Principal principal) {
        User user = getAuthenticatedUser(principal);
        return adminCategoriaDeudaService.findByNombre(nombre, user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nueva categoría para el usuario autenticado
    @PostMapping
    public ResponseEntity<CategoriaDeudaResponseDTO> createCategoria(
            @Valid @RequestBody CategoriaDeudaRequestDTO dto, Principal principal) {
        User user = getAuthenticatedUser(principal);
        CategoriaDeudaResponseDTO created = adminCategoriaDeudaService.create(dto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Actualizar categoría existente (solo si es del usuario autenticado)
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDeudaResponseDTO> updateCategoria(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaDeudaRequestDTO dto,
            Principal principal) {
        User user = getAuthenticatedUser(principal);
        CategoriaDeudaResponseDTO updated = adminCategoriaDeudaService.update(id, dto, user);
        return ResponseEntity.ok(updated);
    }

    // Eliminar categoría (solo si es del usuario autenticado)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Integer id, Principal principal) {
        User user = getAuthenticatedUser(principal);
        adminCategoriaDeudaService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
