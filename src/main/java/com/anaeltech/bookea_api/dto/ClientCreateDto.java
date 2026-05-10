package com.anaeltech.bookea_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientCreateDto(
    @NotBlank @Size(max = 100) String firstname,
    @NotBlank @Size(max = 100) String lastname,
    @NotBlank @Email @Size(max = 150) String email,
    @NotBlank @Size(max = 20) String phone) {
}
