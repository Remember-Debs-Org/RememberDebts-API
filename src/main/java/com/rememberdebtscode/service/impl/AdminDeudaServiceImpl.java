package com.rememberdebtscode.service.impl;

import com.rememberdebtscode.dto.DeudaRequestDTO;
import com.rememberdebtscode.dto.DeudaResponseDTO;
import com.rememberdebtscode.mapper.DeudaMapper;
import com.rememberdebtscode.model.entity.CategoriaDeuda;
import com.rememberdebtscode.model.entity.Deuda;
import com.rememberdebtscode.model.entity.Usuario;
import com.rememberdebtscode.model.enums.EstadoDeuda;
import com.rememberdebtscode.repository.CategoriaDeudaRepository;
import com.rememberdebtscode.repository.DeudaRepository;
import com.rememberdebtscode.repository.UsuarioRepository;
import com.rememberdebtscode.service.AdminDeudaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminDeudaServiceImpl implements AdminDeudaService {

    private final DeudaRepository deudaRepository;
    private final DeudaMapper deudaMapper;
    private final CategoriaDeudaRepository categoriaDeudaRepository;
    private final UsuarioRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<DeudaResponseDTO> getAll() {
        List<Deuda> deudas = deudaRepository.findAll();
        return deudas.stream()
                .map(deudaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeudaResponseDTO> findByCategoriaId(Integer categoriaId) {
        List<Deuda> deudas = deudaRepository.findByCategoriaId(categoriaId);
        return deudas.stream()
                .map(deudaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<DeudaResponseDTO> findByCategoriaNombre(String categoriaNombre) {
        List<Deuda> deudas = deudaRepository.findByCategoriaNombre(categoriaNombre);
        return deudas.stream()
                .map(deudaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public DeudaResponseDTO findById(Integer id) {
        Deuda deuda = deudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada con id: " + id));
        return deudaMapper.toDto(deuda);
    }

    @Override
    public List<DeudaResponseDTO> findByUserId(Integer userId) {
        renovarCiclosRecurrentes(); // <-- Aquí invocas la renovación
        List<Deuda> deudas = deudaRepository.findByUserId(userId);
        return deudas.stream()
                .map(deudaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public DeudaResponseDTO create(DeudaRequestDTO dto) {
        validarFechasSegunEstado(dto);

        // Buscar categoría, EXIGIENDO que pertenezca al usuario
        CategoriaDeuda categoria = categoriaDeudaRepository
            .findByNombreAndUserId(dto.getCategoriaNombre(), dto.getUserId())
            .orElseThrow(() -> new RuntimeException("La categoría no existe o no pertenece al usuario"));

        Usuario user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getUserId()));

        // Mapear DTO a entidad y setear usuario/categoría
        Deuda deuda = deudaMapper.toEntity(dto);
        deuda.setCategoria(categoria);
        deuda.setUser(user);

        // Guardar y devolver
        Deuda saved = deudaRepository.save(deuda);
        return deudaMapper.toDto(saved);
    }

    @Transactional
    @Override
    public DeudaResponseDTO update(Integer id, DeudaRequestDTO dto) {
        validarFechasSegunEstado(dto);

        Deuda deudaExistente = deudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada con id: " + id));

        deudaExistente.setNombre(dto.getNombre());
        deudaExistente.setDescripcion(dto.getDescripcion());
        deudaExistente.setMonto(dto.getMonto());
        deudaExistente.setEstado(dto.getEstado());
        deudaExistente.setRecurrente(dto.getRecurrente());
        deudaExistente.setFrecuencia(dto.getFrecuencia());

        // Fechas
        deudaExistente.setFechaLimitePago(dto.getFechaLimitePago());
        deudaExistente.setFechaPago(dto.getFechaPago());
        deudaExistente.setFechaVencimiento(dto.getFechaVencimiento());

        CategoriaDeuda categoria = categoriaDeudaRepository
            .findByNombreAndUserId(dto.getCategoriaNombre(), dto.getUserId())
            .orElseThrow(() -> new RuntimeException("La categoría no existe o no pertenece al usuario"));
        deudaExistente.setCategoria(categoria);

        Deuda updated = deudaRepository.save(deudaExistente);
        return deudaMapper.toDto(updated);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Deuda deudaExistente = deudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada con id: " + id));
        deudaRepository.delete(deudaExistente);
    }

    private void validarFechasSegunEstado(DeudaRequestDTO dto) {
        EstadoDeuda estado = dto.getEstado();
        boolean rec = Boolean.TRUE.equals(dto.getRecurrente());
        LocalDate hoy = LocalDate.now();

        if (estado == EstadoDeuda.PENDIENTE) {
            if (dto.getFechaLimitePago() == null)
                throw new IllegalArgumentException("La fecha límite de pago es obligatoria para deudas pendientes.");
            if (dto.getFechaPago() != null || dto.getFechaVencimiento() != null)
                throw new IllegalArgumentException("No debe establecer fecha de pago ni de vencimiento para deudas pendientes.");
            // Opcional: No permitir fechas límite pasadas
            if (dto.getFechaLimitePago().isBefore(hoy))
                throw new IllegalArgumentException("La fecha límite de pago no puede ser anterior a hoy para deudas pendientes.");
        }
        else if (estado == EstadoDeuda.PAGADA) {
            if (dto.getFechaPago() == null)
                throw new IllegalArgumentException("La fecha de pago es obligatoria para deudas pagadas.");
            if (dto.getFechaLimitePago() != null && !rec)
                throw new IllegalArgumentException("No debe establecer fecha límite para deudas pagadas no recurrentes.");
            if (dto.getFechaVencimiento() != null)
                throw new IllegalArgumentException("No debe establecer fecha de vencimiento para deudas pagadas.");
            // 🔴 NUEVO: fechaPago no puede ser futura
            if (dto.getFechaPago().isAfter(hoy))
                throw new IllegalArgumentException("La fecha de pago no puede ser posterior a hoy para deudas pagadas.");
            if (rec && (dto.getFechaLimitePago() == null))
                throw new IllegalArgumentException("La fecha límite de pago es obligatoria para deudas pagadas recurrentes.");
            if (rec && dto.getFrecuencia() == null)
                throw new IllegalArgumentException("Debe especificar la frecuencia si la deuda es recurrente.");
            if (!rec && dto.getFrecuencia() != null)
                throw new IllegalArgumentException("No debe especificar frecuencia si la deuda no es recurrente.");
        }
        else if (estado == EstadoDeuda.VENCIDA) {
            if (dto.getFechaVencimiento() == null)
                throw new IllegalArgumentException("La fecha de vencimiento es obligatoria para deudas vencidas.");
            if (dto.getFechaPago() != null)
                throw new IllegalArgumentException("No debe establecer fecha de pago para deudas vencidas.");
            // 🔴 NUEVO: fechaVencimiento no puede ser futura
            if (dto.getFechaVencimiento().isAfter(hoy))
                throw new IllegalArgumentException("La fecha de vencimiento no puede ser posterior a hoy para una deuda vencida.");
            if (rec && (dto.getFechaLimitePago() == null))
                throw new IllegalArgumentException("La fecha límite de pago es obligatoria para deudas vencidas recurrentes.");
            if (rec && dto.getFrecuencia() == null)
                throw new IllegalArgumentException("Debe especificar la frecuencia si la deuda es recurrente.");
            if (!rec && dto.getFrecuencia() != null)
                throw new IllegalArgumentException("No debe especificar frecuencia si la deuda no es recurrente.");
        }
    }

    // Método para actualizar deudas vencidas
    private void verificarVencimientos() {
        List<Deuda> deudasPendientes = deudaRepository.findByEstado(EstadoDeuda.PENDIENTE);
        LocalDate hoy = LocalDate.now();
        for (Deuda d : deudasPendientes) {
            if (d.getFechaLimitePago() != null && d.getFechaLimitePago().isBefore(hoy)) {
                d.setEstado(EstadoDeuda.VENCIDA);
                deudaRepository.save(d);
            }
        }
    }

    @Transactional
    public void renovarCiclosRecurrentes() {
        List<Deuda> deudasPagadasRec = deudaRepository.findByEstadoAndRecurrente(EstadoDeuda.PAGADA, true);
        LocalDate hoy = LocalDate.now();
        for (Deuda d : deudasPagadasRec) {
            // Solo si la fecha límite actual ya pasó y estamos en el siguiente ciclo
            LocalDate siguienteFechaLimite = null;
            if (d.getFrecuencia() != null && d.getFechaLimitePago() != null) {
                switch (d.getFrecuencia()) {
                    case MENSUAL -> siguienteFechaLimite = d.getFechaLimitePago().plusMonths(1);
                    case TRIMESTRAL -> siguienteFechaLimite = d.getFechaLimitePago().plusMonths(3);
                }
                // Si ya estamos en o después de la siguiente fecha límite
                if (siguienteFechaLimite != null && !hoy.isBefore(siguienteFechaLimite)) {
                    d.setEstado(EstadoDeuda.PENDIENTE);
                    d.setFechaLimitePago(siguienteFechaLimite);
                    d.setFechaPago(null);
                    deudaRepository.save(d);
                }
            }
        }
    }

}