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

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @OneToMany(mappedBy = "categoria")
    private List<Deuda> deudas = new ArrayList<>();
}
