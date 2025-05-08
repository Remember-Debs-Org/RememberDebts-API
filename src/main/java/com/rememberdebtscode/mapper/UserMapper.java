package com.rememberdebtscode.mapper;

import com.rememberdebtscode.dto.AuthResponseDTO;
import com.rememberdebtscode.dto.LoginDTO;
import com.rememberdebtscode.dto.UserProfileDTO;
import com.rememberdebtscode.dto.UserRegistrationDTO;
import com.rememberdebtscode.dto.UserResponseDTO;
import com.rememberdebtscode.model.entity.User;
import com.rememberdebtscode.model.entity.Usuario;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public User toUserEntity(UserRegistrationDTO registrationDTO) {
        return modelMapper.map(registrationDTO, User.class);
    }

    public UserProfileDTO toUserProfileDTO(User user) {
        UserProfileDTO userProfileDTO = modelMapper.map(user, UserProfileDTO.class);

        if (user.getUsuario() != null) {
            userProfileDTO.setFirstName(user.getUsuario().getFirstName());
            userProfileDTO.setLastName(user.getUsuario().getLastName());
            userProfileDTO.setTelefono(user.getUsuario().getUserTelefono());
        }

        return userProfileDTO;
    }

    public User toUserEntity(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, User.class);
    }

    public AuthResponseDTO toAuthResponseDTO(User user, String token) {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(token);

        String firstName = (user.getUsuario() != null) ? user.getUsuario().getFirstName() : "Admin";
        String lastName = (user.getUsuario() != null) ? user.getUsuario().getLastName() : "User";

        authResponseDTO.setFirstName(firstName);
        authResponseDTO.setLastName(lastName);

        authResponseDTO.setRole(user.getRole().getNombreRol().name());

        return authResponseDTO;
    }

    // Método existente: de entidad a DTO de respuesta
    public UserResponseDTO toDto(Usuario user) {
        if (user == null)
            return null;
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUserTelefono(user.getUserTelefono());
        dto.setEstado(user.getEstado());
        dto.setCreateAt(user.getCreateAt());
        dto.setUpdateAt(user.getUpdateAt());
        return dto;
    }

    public AuthResponseDTO toAuthResponseDTO(Usuario user, String token) {
        if (user == null || token == null) {
            return null;
        }
        AuthResponseDTO dto = new AuthResponseDTO();
        dto.setToken(token);
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}
