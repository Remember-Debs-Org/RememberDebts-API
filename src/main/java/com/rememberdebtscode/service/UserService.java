package com.rememberdebtscode.service;

import com.rememberdebtscode.dto.AuthResponseDTO;
import com.rememberdebtscode.dto.LoginDTO;
import com.rememberdebtscode.dto.UserProfileDTO;
import com.rememberdebtscode.dto.UserRegistrationDTO;

public interface UserService {
    UserProfileDTO registerUsario(UserRegistrationDTO registrationDTO);

    UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO);

    UserProfileDTO getUserProfileById(Integer id);

    AuthResponseDTO login(LoginDTO loginDTO);
}
