package com.rememberdebtscode.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rememberdebtscode.dto.AuthResponseDTO;
import com.rememberdebtscode.dto.LoginDTO;
import com.rememberdebtscode.dto.UserProfileDTO;
import com.rememberdebtscode.dto.UserRegistrationDTO;
import com.rememberdebtscode.mapper.UserMapper;
import com.rememberdebtscode.model.entity.Role;
import com.rememberdebtscode.model.entity.User;
import com.rememberdebtscode.model.entity.Usuario;
import com.rememberdebtscode.model.enums.ERole;
import com.rememberdebtscode.model.enums.EstadoUsuario;
import com.rememberdebtscode.repository.RoleRepository;
import com.rememberdebtscode.repository.UserRepository;
import com.rememberdebtscode.repository.UsuarioRepository;
import com.rememberdebtscode.security.TokenProvider;
import com.rememberdebtscode.security.UserPrincipal;
import com.rememberdebtscode.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Transactional
    @Override
    public UserProfileDTO registerUsario(UserRegistrationDTO registrationDTO) {
        return registerUserWithRole(registrationDTO, ERole.USUARIO);
    }

    @Transactional
    @Override
    public UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Integer usuarioId = user.getUsuario() != null ? user.getUsuario().getId() : null;

        boolean existAsUsuario = usuarioRepository.existsByFirstNameAndLastNameAndIdNot(
                userProfileDTO.getFirstName(),
                userProfileDTO.getLastName(),
                usuarioId != null ? usuarioId : -1);

        if (existAsUsuario) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo nombre y apellido");
        }

        if (user.getUsuario() == null) {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setEstado(EstadoUsuario.ACTIVO);
            nuevoUsuario.setUser(user);
            user.setUsuario(nuevoUsuario);
        }

        user.getUsuario().setFirstName(userProfileDTO.getFirstName());
        user.getUsuario().setLastName(userProfileDTO.getLastName());

        if (userProfileDTO.getTelefono() != null) {
            user.getUsuario().setUserTelefono(userProfileDTO.getTelefono());
        } else {
            throw new IllegalArgumentException("El teléfono no puede ser nulo");
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toUserProfileDTO(updatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileDTO getUserProfileById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        return userMapper.toUserProfileDTO(user);
    }

    @Transactional
    private UserProfileDTO registerUserWithRole(UserRegistrationDTO registrationDTO, ERole roleEnum) {
        boolean existByEmail = userRepository.existsByUserEmail(registrationDTO.getUserEmail());
        boolean existAsUsuario = usuarioRepository.existsByFirstNameAndLastName(registrationDTO.getFirstName(),
                registrationDTO.getLastName());

        if (existByEmail) {
            throw new IllegalArgumentException("El Email ya está registrado");
        }

        if (existAsUsuario) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo nombre y apellido");
        }

        Role role = roleRepository.findByNombreRol(roleEnum)
                .orElseThrow(() -> new RuntimeException("Error: El Role no existe."));

        registrationDTO.setUserPassword(passwordEncoder.encode(registrationDTO.getUserPassword()));

        User user = userMapper.toUserEntity(registrationDTO);
        user.setRole(role);

        if (roleEnum == ERole.USUARIO || roleEnum == ERole.ADMIN) {
            Usuario usuario = new Usuario();
            usuario.setFirstName(registrationDTO.getFirstName());
            usuario.setLastName(registrationDTO.getLastName());
            usuario.setUserTelefono(registrationDTO.getUserTelefono());
            usuario.setEstado(EstadoUsuario.ACTIVO);
            usuario.setUser(user);
            user.setUsuario(usuario);
        }

        User savedUser = userRepository.save(user);
        return userMapper.toUserProfileDTO(savedUser);
    }

    @Transactional
    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        String token = tokenProvider.createAccessToken(authentication);

        AuthResponseDTO responseDTO = userMapper.toAuthResponseDTO(user, token);

        return responseDTO;
    }
}
