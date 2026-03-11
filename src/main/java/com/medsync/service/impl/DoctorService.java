package com.medsync.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.medsync.entity.Doctor;
import com.medsync.exception.ResourceNotFoundException;
import com.medsync.repository.DoctorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    public Doctor updateDoctor(Long id, Doctor updatedData) {
        Doctor doctor = getDoctorById(id);

        doctor.setSpecialization(updatedData.getSpecialization());
        doctor.setQualification(updatedData.getQualification());
        doctor.setExperienceYears(updatedData.getExperienceYears());
        doctor.setAvailableDays(updatedData.getAvailableDays());
        doctor.setAvailableTime(updatedData.getAvailableTime());
        doctor.setConsultationFee(updatedData.getConsultationFee());

        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        getDoctorById(id);
        doctorRepository.deleteById(id);
    }

    public Doctor saveDoctorProfile(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
}