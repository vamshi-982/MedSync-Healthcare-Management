package com.medsync.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medsync.dto.response.ApiResponse;
import com.medsync.entity.User;
import com.medsync.exception.ResourceNotFoundException;
import com.medsync.repository.DoctorRepository;
import com.medsync.repository.PatientRepository;
import com.medsync.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class AdminController {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @GetMapping("/users")
    @Operation(summary = "Get all registered users (ADMIN only)")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(
                ApiResponse.success("All users fetched", userRepository.findAll())
        );
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete a user (ADMIN only)")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {

        // Check user exists first
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Delete patient profile if exists
        patientRepository.findByUserId(id).ifPresent(patientRepository::delete);

        // Delete doctor profile if exists
        doctorRepository.findByUserId(id).ifPresent(doctorRepository::delete);

        // Now safely delete the user
        userRepository.deleteById(id);

        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }
}