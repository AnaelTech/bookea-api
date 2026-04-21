package com.anaeltech.bookea_api.dto;

public record CancellationStatsDto(
    Long totalAppointments,
    Long cancelled,
    Long noShow,
    double cancellationRate,
    double noShowRate) {
}
