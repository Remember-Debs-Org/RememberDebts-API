package com.rememberdebtscode.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias_deuda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDeuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "categoria")
    private List<Deuda> deudas = new ArrayList<>();
}