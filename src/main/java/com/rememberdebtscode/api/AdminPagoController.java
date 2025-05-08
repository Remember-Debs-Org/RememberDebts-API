package com.rememberdebtscode.api;

import com.rememberdebtscode.dto.PagoRequestDTO;
import com.rememberdebtscode.dto.PagoResponseDTO;
import com.rememberdebtscode.service.AdminPagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/pagos")
@PreAuthorize("hasAnyRole('USUARIO', 'ADMIN')")
public class AdminPagoController {

    private final AdminPagoService adminPagoService;

    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> getAllPagos() {
        List<PagoResponseDTO> pagos = adminPagoService.getAll();
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> getPagoById(@PathVariable Integer id) {
        PagoResponseDTO pago = adminPagoService.findById(id);
        return ResponseEntity.ok(pago);
    }

    @GetMapping("/deuda/{deudaId}")
    public ResponseEntity<List<PagoResponseDTO>> getPagosByDeudaId(@PathVariable Integer deudaId) {
        List<PagoResponseDTO> pagos = adminPagoService.findByDeudaId(deudaId);
        return ResponseEntity.ok(pagos);
    }

    @PostMapping
    public ResponseEntity<PagoResponseDTO> createPago(@Valid @RequestBody PagoRequestDTO dto) {
        PagoResponseDTO createdPago = adminPagoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoResponseDTO> updatePago(
            @PathVariable Integer id,
            @Valid @RequestBody PagoRequestDTO dto) {
        PagoResponseDTO updatedPago = adminPagoService.update(id, dto);
        return ResponseEntity.ok(updatedPago);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Integer id) {
        adminPagoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}