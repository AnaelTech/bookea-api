package com.anaeltech.bookea_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientCreateDto(

    @NotBlank String firstname,
    @NotBlank String lastname,
    @NotBlank @Email String email,
    @NotBlank @Size(max = 20) String phone) {
}
