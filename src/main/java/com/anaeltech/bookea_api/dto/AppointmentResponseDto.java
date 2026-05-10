package com.anaeltech.bookea_api.dto;

import java.time.LocalDateTime;

import com.anaeltech.bookea_api.entity.AppointmentStatus;

public record AppointmentResponseDto(Long id, UserResponseDto user, ClientResponseDto client, LocalDateTime startAt,
    LocalDateTime endAt, AppointmentStatus status, String notes) {

}
