package com.rememberdebtscode.service;

import com.rememberdebtscode.model.entity.User;
import com.rememberdebtscode.model.enums.EstadoUsuario;

import java.util.List;

public interface AdminUserService {
    List<User> getAll();
    List<User> getByEstado(EstadoUsuario estado);
    User findById(Integer id);
    User create(User user);
    User update(Integer id, User user);
    User suspend(Integer id);
    void delete(Integer id);
}
