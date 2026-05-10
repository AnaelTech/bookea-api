package com.anaeltech.bookea_api.controller;

import org.springframework.data.domain.Page;
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
import org.springframework.data.domain.Pageable;

import com.anaeltech.bookea_api.dto.UserCreateDto;
import com.anaeltech.bookea_api.dto.UserResponseDto;
import com.anaeltech.bookea_api.dto.UserUpdateDto;
import com.anaeltech.bookea_api.entity.User;
import com.anaeltech.bookea_api.exceptions.UserNotFoundException;
import com.anaeltech.bookea_api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

@RestController
@RequestMapping("api/v1/users")
@Tag(name = "User", description = "User related operations")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Operation(summary = "Get all users", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved all users", content = @Content(schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "503", description = "Service unavailable", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping
  public ResponseEntity<Page<UserResponseDto>> getAllUsers(
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

    Page<UserResponseDto> users = userService.findAll(pageable);
    return ResponseEntity.ok(users);
  }

  @Operation(summary = "Get user by id", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved user", content = @Content(schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = UserNotFoundException.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })
  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getUserById(
      @Parameter(description = "User id", example = "1") @PathVariable("id") Long id) {
    return ResponseEntity.ok(userService.findById(id));
  }

  @Operation(summary = "Get user by email", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved user", content = @Content(schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = UserNotFoundException.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })
  @GetMapping("/by-email")
  public ResponseEntity<UserResponseDto> getUserByEmail(
      @Parameter(description = "User email", example = "user@example.com") @RequestParam("email") @Email(message = "Email is not valid") String email) {
    return ResponseEntity.ok(userService.findByEmail(email));
  }

  @Operation(summary = "Create user", responses = {
      @ApiResponse(responseCode = "201", description = "Successfully created user", content = @Content(schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })
  @PostMapping
  public ResponseEntity<UserResponseDto> createUser(
      @Parameter(description = "User to create") @Valid @RequestBody UserCreateDto user) {
    UserResponseDto userResponseDto = userService.createUser(user);
    return ResponseEntity.status(201).body(userResponseDto);
  }

  @Operation(summary = "Update user", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully updated user", content = @Content(schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = UserNotFoundException.class))),
  })
  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDto> updateUser(
      @Parameter(description = "User to update") @PathVariable Long id,
      @Valid @RequestBody UserUpdateDto user) {
    UserResponseDto userResponseDto = userService.updateUser(id, user);
    return ResponseEntity.ok(userResponseDto);
  }

  @Operation(summary = "Delete user", responses = {
      @ApiResponse(responseCode = "204", description = "Successfully deleted user", content = @Content(schema = @Schema(implementation = User.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = UserNotFoundException.class))),
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@Parameter(description = "User to delete") @PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

}
