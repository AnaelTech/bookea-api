package com.anaeltech.bookea_api.dto;

public record StatsOverviewDto(
    double fillRate,
    Long totalAppointments,
    Long cancelledAppointments,
    Long noShowAppointments,
    Long totalClients,
    Long newClients) {
}
