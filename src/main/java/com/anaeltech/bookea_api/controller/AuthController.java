package com.anaeltech.bookea_api.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anaeltech.bookea_api.dto.AuthResponseDto;
import com.anaeltech.bookea_api.dto.LoginRequestDto;
import com.anaeltech.bookea_api.entity.User;
import com.anaeltech.bookea_api.exceptions.InvalidCredentialsException;
import com.anaeltech.bookea_api.repository.UserRepository;
import com.anaeltech.bookea_api.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
@Tag(name = "Auth", description = "Authentification user")
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  @Operation(summary = "Login user", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully login, token user", content = @Content(schema = @Schema(implementation = AuthResponseDto.class))),
      @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })

  @PostMapping("/login")
  public AuthResponseDto login(
      @Valid @RequestBody LoginRequestDto request) {

    System.out.println(request.email());
    System.out.println(request.password());
    User user = userRepository.findByEmail(request.email())
        .orElseThrow(() -> new InvalidCredentialsException());

    boolean matches = passwordEncoder.matches(request.password(), user.getPassword());

    if (!matches) {
      throw new InvalidCredentialsException();
    }
    String token = jwtUtil.generateToken(user.getId());

    return new AuthResponseDto(token);
  }

}
