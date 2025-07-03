package com.rememberdebtscode.api;

import com.rememberdebtscode.dto.PagoRequestDTO;
import com.rememberdebtscode.dto.PagoResponseDTO;
import com.rememberdebtscode.service.AdminPagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    // --- CAMBIO PRINCIPAL: acepta multipart para comprobante ---
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PagoResponseDTO> createPago(
            @RequestPart("pago") @Valid PagoRequestDTO dto,
            @RequestPart(value = "comprobante", required = false) MultipartFile comprobante) {
        PagoResponseDTO createdPago = adminPagoService.create(dto, comprobante);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPago);
    }

    // Si decides no permitir actualizar ni borrar pagos, puedes comentar estos métodos.
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
