package com.rememberdebtscode.api;

import com.rememberdebtscode.dto.DeudaRequestDTO;
import com.rememberdebtscode.dto.DeudaResponseDTO;
import com.rememberdebtscode.service.AdminDeudaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/deudas")
@PreAuthorize("hasAnyRole('USUARIO', 'ADMIN')")
public class AdminDeudaController {

    private final AdminDeudaService adminDeudaService;

    @GetMapping
    public ResponseEntity<List<DeudaResponseDTO>> getAllDeudas() {
        List<DeudaResponseDTO> deudas = adminDeudaService.getAll();
        return ResponseEntity.ok(deudas);
    }

    @GetMapping("/categoria/{categoriaNombre}")
    public ResponseEntity<List<DeudaResponseDTO>> getDeudasByCategoria(@PathVariable String categoriaNombre) {
        List<DeudaResponseDTO> deudas = adminDeudaService.findByCategoriaNombre(categoriaNombre);
        return ResponseEntity.ok(deudas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeudaResponseDTO> getDeudaById(@PathVariable Integer id) {
        DeudaResponseDTO deuda = adminDeudaService.findById(id);
        return ResponseEntity.ok(deuda);
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<DeudaResponseDTO>> getDeudasByUserId(@PathVariable Integer userId) {
        List<DeudaResponseDTO> deudas = adminDeudaService.findByUserId(userId);
        return ResponseEntity.ok(deudas);
    }

    @PostMapping
    public ResponseEntity<DeudaResponseDTO> createDeuda(@Valid @RequestBody DeudaRequestDTO dto) {
        DeudaResponseDTO createdDeuda = adminDeudaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDeuda);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeudaResponseDTO> updateDeuda(
            @PathVariable Integer id,
            @Valid @RequestBody DeudaRequestDTO dto) {
        DeudaResponseDTO updatedDeuda = adminDeudaService.update(id, dto);
        return ResponseEntity.ok(updatedDeuda);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeuda(@PathVariable Integer id) {
        adminDeudaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
