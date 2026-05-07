package com.anaeltech.bookea_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
    @NotBlank @Email @Size(max = 150) @JsonProperty("email") String email,
    @NotBlank @Size(min = 8, max = 255) @JsonProperty("password") String password) {

}
