package com.rememberdebtscode.security;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rememberdebtscode.model.entity.User;
import com.rememberdebtscode.model.enums.EstadoUsuario;
import com.rememberdebtscode.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + email));

        if (user.getUsuario().getEstado() == EstadoUsuario.ELIMINADO) {
            throw new RuntimeException("Usuario eliminado no puede iniciar sesión");
            // O lanzar UsernameNotFoundException o DisabledException según convenga
        }

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().getNombreRol().name());

        return new UserPrincipal(user.getId(), user.getUserEmail(), user.getUserPassword(),
                Collections.singletonList(authority), user);
    }

}
