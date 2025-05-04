package com.rememberdebtscode.service.impl;

import com.rememberdebtscode.model.entity.User;
import com.rememberdebtscode.model.enums.EstadoUsuario;
import com.rememberdebtscode.repository.UserRepository;
import com.rememberdebtscode.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<User> getAll() {
        return userRepository.findByEstado(EstadoUsuario.ACTIVO);
    }

    @Override
    public List<User> getByEstado(EstadoUsuario estado) {
        return userRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    @Transactional
    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User update(Integer id, User updateUser) {
        User userFromDb = findById(id);
        userFromDb.setFirstName(updateUser.getFirstName());
        userFromDb.setLastName(updateUser.getLastName());
        userFromDb.setUserPassword(updateUser.getUserPassword());
        userFromDb.setUserTelefono(updateUser.getUserTelefono());
        return userRepository.save(userFromDb);
    }

    @Transactional
    @Override
    public User suspend(Integer id) {
        User user = findById(id);
        user.setEstado(EstadoUsuario.SUSPENDIDO);
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        User user = findById(id);
        user.setEstado(EstadoUsuario.ELIMINADO);
        user.setDeletedAt(LocalDate.now());
        userRepository.save(user);
    }
}
