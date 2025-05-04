package com.rememberdebtscode.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deuda_id", nullable = false)
    private Deuda deuda;

    @Column(name = "fecha_alerta", nullable = false)
    private LocalDate fechaAlerta;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "enviada", nullable = false)
    private Boolean enviada = false;

    @Column(name = "mensaje")
    private String mensaje;
}
