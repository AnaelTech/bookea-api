package com.anaeltech.bookea_api.dto;

import java.time.LocalDateTime;

import com.anaeltech.bookea_api.entity.AppointmentStatus;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record AppointmentCreateDto(@NotNull Long userId, @NotNull Long clientId, @NotNull @Future LocalDateTime startAt,
    @NotNull @Future LocalDateTime endAt, AppointmentStatus status, String notes) {

}
