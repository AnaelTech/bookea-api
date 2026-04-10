package com.anaeltech.bookea_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anaeltech.bookea_api.dto.UserCreateDto;
import com.anaeltech.bookea_api.dto.UserResponseDto;
import com.anaeltech.bookea_api.dto.UserUpdateDto;

public interface UserService {

  Page<UserResponseDto> findAll(Pageable pageable);

  UserResponseDto findById(Long id);

  UserResponseDto createUser(UserCreateDto userCreateDto);

  UserResponseDto findByEmail(String email);

  void deleteUser(Long id);

  UserResponseDto updateUser(UserUpdateDto userUpdateDto);

}
