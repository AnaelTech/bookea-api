package com.anaeltech.bookea_api.dto;

public record ClientStatsDto(
    Long totalClients,
    Long newClients,
    Long returningClients) {

}
