package com.anaeltech.bookea_api.service;

import java.time.LocalDateTime;

import com.anaeltech.bookea_api.dto.CancellationStatsDto;
import com.anaeltech.bookea_api.dto.ClientStatsDto;
import com.anaeltech.bookea_api.dto.OccupancyStatsDto;
import com.anaeltech.bookea_api.dto.StatsOverviewDto;

public interface StatsService {

  StatsOverviewDto getStatsOverview(Long userId, LocalDateTime start, LocalDateTime end);

  OccupancyStatsDto getOccupancyStats(Long userId, LocalDateTime start, LocalDateTime end);

  CancellationStatsDto getCancellationStats(Long userId, LocalDateTime start, LocalDateTime end);

  ClientStatsDto getClientStats(Long userId, LocalDateTime start, LocalDateTime end);

}
