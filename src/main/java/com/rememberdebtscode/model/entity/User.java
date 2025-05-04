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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_nombre", nullable = false)
    private String firstName;

    @Column(name = "user_apellido", nullable = false)
    private String lastName;

    @Column(name = "user_email", nullable = false, unique = true, updatable = false)
    private String userEmail;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_telefono")
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

}
