package com.rememberdebtscode.api;

import com.rememberdebtscode.dto.AlertaRequestDTO;
import com.rememberdebtscode.dto.AlertaResponseDTO;
import com.rememberdebtscode.service.AdminAlertaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/alertas")
@PreAuthorize("hasAnyRole('USUARIO', 'ADMIN')")
public class AdminAlertaController {

    private final AdminAlertaService adminAlertaService;

    @GetMapping
    public ResponseEntity<List<AlertaResponseDTO>> getAllAlertas() {
        return ResponseEntity.ok(adminAlertaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaResponseDTO> getAlertaById(@PathVariable Integer id) {
        AlertaResponseDTO alerta = adminAlertaService.findById(id);
        return ResponseEntity.ok(alerta);
    }

    @GetMapping("/deuda/{deudaId}")
    public ResponseEntity<List<AlertaResponseDTO>> getAlertasByDeudaId(@PathVariable Integer deudaId) {
        return ResponseEntity.ok(adminAlertaService.findByDeudaId(deudaId));
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<AlertaResponseDTO>> getPendingAlertas() {
        return ResponseEntity.ok(adminAlertaService.findPendingAlerts());
    }

    @PostMapping
    public ResponseEntity<AlertaResponseDTO> createAlerta(@Valid @RequestBody AlertaRequestDTO dto) {
        AlertaResponseDTO created = adminAlertaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertaResponseDTO> updateAlerta(
            @PathVariable Integer id,
            @Valid @RequestBody AlertaRequestDTO dto) {
        AlertaResponseDTO updated = adminAlertaService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlerta(@PathVariable Integer id) {
        adminAlertaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}