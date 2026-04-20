package com.anaeltech.bookea_api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anaeltech.bookea_api.dto.ClientCreateDto;
import com.anaeltech.bookea_api.dto.ClientResponseDto;
import com.anaeltech.bookea_api.dto.ClientUpdateDto;
import com.anaeltech.bookea_api.exceptions.ClientNotFoundException;
import com.anaeltech.bookea_api.exceptions.UserNotFoundException;
import com.anaeltech.bookea_api.service.ClientService;
import com.anaeltech.bookea_api.entity.Client;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

  @Operation(summary = "Get client by id", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved client", content = @Content(schema = @Schema(implementation = Client.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(schema = @Schema(implementation = ClientNotFoundException.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<ClientResponseDto> getClientById(
      @Parameter(description = "Client id", example = "1") @PathVariable("id") Long id) {
    return ResponseEntity.ok(clientService.findById(id));
  }

  @Operation(summary = "Create client", responses = {
      @ApiResponse(responseCode = "201", description = "Successfully created client", content = @Content(schema = @Schema(implementation = Client.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })
  @PostMapping
  public ResponseEntity<ClientResponseDto> createClient(
      @Parameter(description = "Client to create") @Valid @RequestBody ClientCreateDto client) {
    ClientResponseDto clientResponseDto = clientService.createClient(client);
    return ResponseEntity.status(201).body(clientResponseDto);
  }

  @Operation(summary = "Update client", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully updated client", content = @Content(schema = @Schema(implementation = Client.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(schema = @Schema(implementation = ClientNotFoundException.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })
  @PutMapping("/{id}")
  public ResponseEntity<ClientResponseDto> updateClient(
      @Parameter(description = "Client to update") @PathVariable Long id,
      @RequestBody ClientUpdateDto client) {
    ClientResponseDto clientResponseDto = clientService.updateClient(id, client);
    return ResponseEntity.ok(clientResponseDto);
  }

  @Operation(summary = "Delete client", responses = {
      @ApiResponse(responseCode = "204", description = "Successfully deleted client", content = @Content(schema = @Schema(implementation = Client.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(schema = @Schema(implementation = ClientNotFoundException.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteClient(@Parameter(description = "Client to delete") @PathVariable Long id) {
    clientService.deleteClient(id);
    return ResponseEntity.noContent().build();
  }
}
