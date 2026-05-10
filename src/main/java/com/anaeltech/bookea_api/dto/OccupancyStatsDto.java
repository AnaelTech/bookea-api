package com.anaeltech.bookea_api.dto;

public record OccupancyStatsDto(
    Long totalAvailableMinutes,
    Long bookedMinutes,
    Long freeMinutes,
    double fillRate) {
}
