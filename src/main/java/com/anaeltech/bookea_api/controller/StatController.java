package com.anaeltech.bookea_api.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anaeltech.bookea_api.dto.ErrorResponse;
import com.anaeltech.bookea_api.dto.OccupancyStatsDto;
import com.anaeltech.bookea_api.dto.StatsOverviewDto;
import com.anaeltech.bookea_api.exceptions.UserNotFoundException;
import com.anaeltech.bookea_api.service.StatsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/stats")
@Tag(name = "Stat", description = "Stat related operations")
public class StatController {

  private final StatsService statsService;

  public StatController(StatsService statsService) {
    this.statsService = statsService;
  }

  @Operation(summary = "Get overview stats", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved all stats", content = @Content(schema = @Schema(implementation = StatsOverviewDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = UserNotFoundException.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/overview/{id}")
  public ResponseEntity<StatsOverviewDto> getStatsOverview(
      @Parameter(description = "User id", example = "1") @PathVariable("id") Long id,
      @Parameter(description = "Start date", example = "2022-01-01") @RequestParam("start") LocalDateTime start,
      @Parameter(description = "End date", example = "2022-01-31") @RequestParam("end") LocalDateTime end) {

    return ResponseEntity.ok(statsService.getStatsOverview(id, start, end));
  }

  @Operation(summary = "Get occupancy stats", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved stats", content = @Content(schema = @Schema(implementation = OccupancyStatsDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = UserNotFoundException.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/occupancy/{id}")
  public ResponseEntity<OccupancyStatsDto> getOccupancyStats(
      @Parameter(description = "User id", example = "1") @PathVariable("id") Long id,
      @Parameter(description = "Start date", example = "2022-01-01") @RequestParam("start") LocalDateTime start,
      @Parameter(description = "End date", example = "2022-01-31") @RequestParam("end") LocalDateTime end) {
    return ResponseEntity.ok(statsService.getOccupancyStats(id, start, end));
  }

}
