package com.rememberdebtscode.model.entity;

import com.rememberdebtscode.model.enums.EstadoDeuda;
import com.rememberdebtscode.model.enums.FrecuenciaDeuda;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "debts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario user;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaDeuda categoria;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "fecha_limite_pago")
    private LocalDate fechaLimitePago;      // Para deudas pendientes

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;            // Para deudas pagadas

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;     // Para deudas vencidas

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoDeuda estado;

    @Column(name = "recurrente", nullable = false)
    private Boolean recurrente = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "frecuencia")
    private FrecuenciaDeuda frecuencia;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDate fechaCreacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDate.now();
    }

    @OneToMany(mappedBy = "deuda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pago> pagos = new ArrayList<>();
}
