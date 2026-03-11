package com.medsync.controller;

import com.medsync.dto.request.PatientProfileRequest;
import com.medsync.dto.response.ApiResponse;
import com.medsync.entity.Patient;
import com.medsync.entity.User;
import com.medsync.exception.ResourceNotFoundException;
import com.medsync.repository.PatientRepository;
import com.medsync.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
@Tag(name = "Patients", description = "Patient management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class PatientController {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    @PostMapping("/profile/complete")
    @Operation(summary = "Patient completes their profile (PATIENT only)")
    public ResponseEntity<ApiResponse<Patient>> completeProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PatientProfileRequest request) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Patient patient = Patient.builder()
                .user(user)
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .bloodGroup(request.getBloodGroup())
                .address(request.getAddress())
                .medicalHistory(request.getMedicalHistory())
                .build();

        return ResponseEntity.ok(
                ApiResponse.success("Patient profile completed!", patientRepository.save(patient))
        );
    }

    @GetMapping("/profile")
    @Operation(summary = "Patient views their own profile (PATIENT only)")
    public ResponseEntity<ApiResponse<Patient>> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Patient patient = patientRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient profile not found"));

        return ResponseEntity.ok(ApiResponse.success("Profile fetched", patient));
    }
}