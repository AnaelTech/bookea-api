package com.anaeltech.bookea_api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import com.anaeltech.bookea_api.service.AppointmentService;
import com.anaeltech.bookea_api.dto.AppointmentCreateDto;
import com.anaeltech.bookea_api.dto.AppointmentResponseDto;
import com.anaeltech.bookea_api.dto.AppointmentUpdateDto;
import com.anaeltech.bookea_api.dto.ErrorResponse;
import com.anaeltech.bookea_api.entity.Appointment;
import com.anaeltech.bookea_api.exceptions.AppointmentNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/appointment")
@Tag(name = "Appointment", description = "Appointment related operations")
public class AppointmentController {
  private final AppointmentService appointmentService;

  public AppointmentController(AppointmentService appointmentService) {
    this.appointmentService = appointmentService;
  }

  @Operation(summary = "Get all appointments", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved all appointments", content = @Content(schema = @Schema(implementation = Appointment.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })
  @GetMapping
  public ResponseEntity<Page<AppointmentResponseDto>> getAllAppointments(
      @Parameter(description = "Page number (starts at 0)", example = "0") @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size,
      @Parameter(description = "Sort field", example = "id") @RequestParam(defaultValue = "id") String sortBy,
      @Parameter(description = "Sort direction", example = "ASC") @RequestParam(defaultValue = "ASC") String direction) {
    if (size > 100)
      size = 100;
    if (page < 0)
      page = 0;
    Sort sort = Sort.by(Sort.Direction.fromString(direction.toUpperCase()), sortBy);
    Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, sort);
    Page<AppointmentResponseDto> appointments = appointmentService.findAll(pageable);
    return ResponseEntity.ok(appointments);
  }

  @Operation(summary = "Get appointment by ID", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved appointment", content = @Content(schema = @Schema(implementation = AppointmentResponseDto.class))),
      @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content(schema = @Schema(implementation = AppointmentNotFoundException.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })
  @GetMapping("/{id}")
  public ResponseEntity<AppointmentResponseDto> getAppointmentById(
      @Parameter(description = "Appointment ID", example = "1", required = true) @PathVariable Long id) {

    AppointmentResponseDto appointment = appointmentService.findById(id);
    return ResponseEntity.ok(appointment);
  }

  @Operation(summary = "Create a new appointment", responses = {
      @ApiResponse(responseCode = "201", description = "Appointment created successfully", content = @Content(schema = @Schema(implementation = AppointmentResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request or appointment conflict", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "User or Client not found", content = @Content(schema = @Schema(implementation = AppointmentNotFoundException.class))),
      @ApiResponse(responseCode = "409", description = "Appointment already reserved", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })
  @PostMapping
  public ResponseEntity<AppointmentResponseDto> createAppointment(
      @Parameter(description = "Appointment creation data", required = true) @Valid @RequestBody AppointmentCreateDto appointmentCreateDto) {

    AppointmentResponseDto createdAppointment = appointmentService.createAppointment(appointmentCreateDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdAppointment);
  }

  @Operation(summary = "Update an existing appointment", responses = {
      @ApiResponse(responseCode = "200", description = "Appointment updated successfully", content = @Content(schema = @Schema(implementation = AppointmentResponseDto.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content(schema = @Schema(implementation = AppointmentNotFoundException.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })
  @PutMapping("/{id}")
  public ResponseEntity<AppointmentResponseDto> updateAppointment(
      @Parameter(description = "Appointment ID", example = "1", required = true) @PathVariable Long id,
      @Parameter(description = "Appointment update data", required = true) @Valid @RequestBody AppointmentUpdateDto appointmentUpdateDto) {

    AppointmentResponseDto updatedAppointment = appointmentService.updateAppointment(id, appointmentUpdateDto);
    return ResponseEntity.ok(updatedAppointment);
  }

  @Operation(summary = "Delete an appointment", responses = {
      @ApiResponse(responseCode = "204", description = "Appointment deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Appointment not found", content = @Content(schema = @Schema(implementation = AppointmentNotFoundException.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAppointment(
      @Parameter(description = "Appointment ID", example = "1", required = true) @PathVariable Long id) {

    appointmentService.deleteAppointment(id);
    return ResponseEntity.noContent().build();
  }
}
