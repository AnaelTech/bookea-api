package com.anaeltech.bookea_api.dto;

import java.time.LocalDateTime;

import com.anaeltech.bookea_api.entity.AppointmentStatus;

public record AppointmentUpdateDto(
    LocalDateTime startAt, LocalDateTime endAt, AppointmentStatus status, String notes) {
}
