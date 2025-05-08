package com.rememberdebtscode.api;

import com.rememberdebtscode.dto.CategoriaDeudaRequestDTO;
import com.rememberdebtscode.dto.CategoriaDeudaResponseDTO;
import com.rememberdebtscode.service.AdminCategoriaDeudaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/categorias-deuda")
@PreAuthorize("hasAnyRole('USUARIO', 'ADMIN')")
public class AdminCategoriaDeudaController {

    private final AdminCategoriaDeudaService adminCategoriaDeudaService;

    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoriaDeudaResponseDTO>> getAllCategorias() {
        List<CategoriaDeudaResponseDTO> categorias = adminCategoriaDeudaService.getAll();
        return ResponseEntity.ok(categorias);
    }

    // Obtener categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDeudaResponseDTO> getCategoriaById(@PathVariable Integer id) {
        return adminCategoriaDeudaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener categoría por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CategoriaDeudaResponseDTO> getCategoriaByNombre(@PathVariable String nombre) {
        return adminCategoriaDeudaService.findByNombre(nombre)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nueva categoría
    @PostMapping
    public ResponseEntity<CategoriaDeudaResponseDTO> createCategoria(
            @Valid @RequestBody CategoriaDeudaRequestDTO dto) {
        CategoriaDeudaResponseDTO created = adminCategoriaDeudaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Actualizar categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDeudaResponseDTO> updateCategoria(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaDeudaRequestDTO dto) {
        CategoriaDeudaResponseDTO updated = adminCategoriaDeudaService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Eliminar categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Integer id) {
        adminCategoriaDeudaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
