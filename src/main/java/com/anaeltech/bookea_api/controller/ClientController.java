package com.anaeltech.bookea_api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anaeltech.bookea_api.dto.ClientResponseDto;
import com.anaeltech.bookea_api.exceptions.UserNotFoundException;
import com.anaeltech.bookea_api.service.ClientService;
import com.anaeltech.bookea_api.entity.Client;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("api/v1/client")
@Tag(name = "Client", description = "Client related operations")
public class ClientController {

  private final ClientService clientService;

  public ClientController(ClientService clientService) {
    this.clientService = clientService;
  }

  @Operation(summary = "Get all clients", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved all clients", content = @Content(schema = @Schema(implementation = Client.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(schema = @Schema(implementation = UserNotFoundException.class)))
  })
  @GetMapping
  public ResponseEntity<Page<ClientResponseDto>> getAllClients(
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

    Page<ClientResponseDto> clients = clientService.findAll(pageable);
    return ResponseEntity.ok(clients);
  }
}
