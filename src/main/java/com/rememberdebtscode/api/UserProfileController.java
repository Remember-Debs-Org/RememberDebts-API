package com.rememberdebtscode.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rememberdebtscode.dto.UserProfileDTO;
import com.rememberdebtscode.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/user/profile")
@PreAuthorize("hasRole('USUARIO')")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDTO> updateProfile(@PathVariable Integer id,
            @Valid @RequestBody UserProfileDTO userProfileDTO) {
        UserProfileDTO updatedProfile = userService.updateUserProfile(id, userProfileDTO);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getProfile(@PathVariable Integer id) {
        UserProfileDTO userProfile = userService.getUserProfileById(id);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }
}
