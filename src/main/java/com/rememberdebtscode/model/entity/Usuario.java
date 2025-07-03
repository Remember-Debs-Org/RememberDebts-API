package com.rememberdebtscode.model.entity;

import com.rememberdebtscode.model.enums.EstadoUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_nombre", nullable = false)
    private String firstName;

    @Column(name = "user_apellido", nullable = false)
    private String lastName;

    @Column(name = "user_telefono", nullable = false)
    private String userTelefono;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createAt;

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDate.now();
    }

    @Column(name = "update_at")
    private LocalDate updateAt;

    @PreUpdate
    public void preUpdate() {
        this.updateAt = LocalDate.now();
    }

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_usuario", nullable = false)
    private EstadoUsuario estado;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
