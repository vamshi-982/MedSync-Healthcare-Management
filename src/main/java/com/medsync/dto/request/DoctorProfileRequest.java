package com.medsync.dto.request;

import lombok.Data;

@Data
public class DoctorProfileRequest {
    private String specialization;
    private String qualification;
    private Integer experienceYears;
    private String availableDays;
    private String availableTime;
    private Double consultationFee;
}