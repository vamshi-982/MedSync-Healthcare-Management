package com.medsync.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medsync.dto.request.DoctorProfileRequest;
import com.medsync.dto.response.ApiResponse;
import com.medsync.entity.Doctor;
import com.medsync.entity.User;
import com.medsync.exception.ResourceNotFoundException;
import com.medsync.repository.DoctorRepository;
import com.medsync.repository.UserRepository;
import com.medsync.service.impl.DoctorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Doctors", description = "Doctor management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class DoctorController {

    private final DoctorService doctorService;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    // Any authenticated user can view doctors
    @GetMapping("/doctors")
    @Operation(summary = "Get all doctors")
    public ResponseEntity<ApiResponse<List<Doctor>>> getAllDoctors() {
        return ResponseEntity.ok(
                ApiResponse.success("Doctors fetched", doctorService.getAllDoctors())
        );
    }

    // Get doctor by ID
    @GetMapping("/doctors/{id}")
    @Operation(summary = "Get doctor by ID")
    public ResponseEntity<ApiResponse<Doctor>> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("Doctor fetched", doctorService.getDoctorById(id))
        );
    }

    // Search doctors by specialization
    @GetMapping("/doctors/specialization/{specialization}")
    @Operation(summary = "Search doctors by specialization")
    public ResponseEntity<ApiResponse<List<Doctor>>> getBySpecialization(
            @PathVariable String specialization) {
        return ResponseEntity.ok(
                ApiResponse.success("Doctors fetched",
                        doctorService.getDoctorsBySpecialization(specialization))
        );
    }

    // Doctor completes their profile after registration
    @PostMapping("/doctor/profile/complete")
    @Operation(summary = "Doctor completes their profile (DOCTOR only)")
    public ResponseEntity<ApiResponse<Doctor>> completeProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody DoctorProfileRequest request) {

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Doctor doctor = Doctor.builder()
                .user(user)
                .specialization(request.getSpecialization())
                .qualification(request.getQualification())
                .experienceYears(request.getExperienceYears())
                .availableDays(request.getAvailableDays())
                .availableTime(request.getAvailableTime())
                .consultationFee(request.getConsultationFee())
                .build();

        return ResponseEntity.ok(
                ApiResponse.success("Profile completed!", doctorRepository.save(doctor))
        );
    }

    // Doctor updates their own profile
    @PutMapping("/doctor/profile/{id}")
    @Operation(summary = "Doctor updates their profile (DOCTOR only)")
    public ResponseEntity<ApiResponse<Doctor>> updateProfile(
            @PathVariable Long id,
            @RequestBody DoctorProfileRequest request) {

        Doctor existing = doctorService.getDoctorById(id);
        existing.setSpecialization(request.getSpecialization());
        existing.setQualification(request.getQualification());
        existing.setExperienceYears(request.getExperienceYears());
        existing.setAvailableDays(request.getAvailableDays());
        existing.setAvailableTime(request.getAvailableTime());
        existing.setConsultationFee(request.getConsultationFee());

        return ResponseEntity.ok(
                ApiResponse.success("Profile updated", doctorRepository.save(existing))
        );
    }

    // Admin deletes a doctor
    @DeleteMapping("/admin/doctors/{id}")
    @Operation(summary = "Delete a doctor (ADMIN only)")
    public ResponseEntity<ApiResponse<Void>> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok(ApiResponse.success("Doctor deleted", null));
    }
}