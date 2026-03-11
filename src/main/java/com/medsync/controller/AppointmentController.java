package com.medsync.controller;

import com.medsync.dto.request.AppointmentRequest;
import com.medsync.dto.response.ApiResponse;
import com.medsync.entity.Appointment;
import com.medsync.service.impl.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Appointments", description = "Appointment booking and management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class AppointmentController {

    private final AppointmentService appointmentService;

    // Patient books appointment
    @PostMapping("/patient/appointments/book")
    @Operation(summary = "Book an appointment (PATIENT only)")
    public ResponseEntity<ApiResponse<Appointment>> bookAppointment(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody AppointmentRequest request) {
        Appointment appointment = appointmentService.bookAppointment(userDetails.getUsername(), request);
        return ResponseEntity.ok(ApiResponse.success("Appointment booked successfully", appointment));
    }

    // Patient views their own appointments
    @GetMapping("/patient/appointments")
    @Operation(summary = "Get my appointments (PATIENT only)")
    public ResponseEntity<ApiResponse<List<Appointment>>> getMyAppointments(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<Appointment> appointments = appointmentService.getMyAppointments(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Appointments fetched", appointments));
    }

    // Patient cancels appointment
    @PutMapping("/patient/appointments/{id}/cancel")
    @Operation(summary = "Cancel an appointment (PATIENT only)")
    public ResponseEntity<ApiResponse<Appointment>> cancelAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Appointment cancelled", appointmentService.cancelAppointment(id)));
    }

    // Doctor views their appointments
    @GetMapping("/doctor/appointments")
    @Operation(summary = "Get doctor's appointments (DOCTOR only)")
    public ResponseEntity<ApiResponse<List<Appointment>>> getDoctorAppointments(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<Appointment> appointments = appointmentService.getDoctorAppointments(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Appointments fetched", appointments));
    }

    // Doctor updates appointment status
    @PutMapping("/doctor/appointments/{id}/status")
    @Operation(summary = "Update appointment status (DOCTOR only) - CONFIRMED or COMPLETED")
    public ResponseEntity<ApiResponse<Appointment>> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated", appointmentService.updateStatus(id, status)));
    }

    // Admin views all appointments
    @GetMapping("/admin/appointments")
    @Operation(summary = "Get all appointments (ADMIN only)")
    public ResponseEntity<ApiResponse<List<Appointment>>> getAllAppointments() {
        return ResponseEntity.ok(ApiResponse.success("All appointments fetched", appointmentService.getAllAppointments()));
    }
}
