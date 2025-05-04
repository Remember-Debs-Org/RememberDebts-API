package com.rememberdebtscode.api;

import com.rememberdebtscode.model.entity.User;
import com.rememberdebtscode.model.enums.EstadoUsuario;
import com.rememberdebtscode.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = adminUserService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<User>> getUsersByEstado(@PathVariable EstadoUsuario estado) {
        List<User> users = adminUserService.getByEstado(estado);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        User user = adminUserService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = adminUserService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody User user) {
        User updateUser = adminUserService.update(id, user);
        return ResponseEntity.ok(updateUser);
    }

    @PatchMapping("/{id}/suspender")
    public ResponseEntity<User> suspenderUsuario(@PathVariable("id") Integer id) {
        User user = adminUserService.suspend(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        adminUserService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
