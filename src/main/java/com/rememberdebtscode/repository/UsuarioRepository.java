package com.rememberdebtscode.repository;

import com.rememberdebtscode.model.entity.Usuario;
import com.rememberdebtscode.model.enums.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByEstado(EstadoUsuario estado);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByFirstNameAndLastNameAndIdNot(String firstName, String lastName, Integer id);
}
