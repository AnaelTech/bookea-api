package com.anaeltech.bookea_api.dto;

public record UserResponseDto(
    Long id,
    String firstname,
    String lastname,
    String email,
    String phone) {
}
