package com.anaeltech.bookea_api.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anaeltech.bookea_api.dto.UserCreateDto;
import com.anaeltech.bookea_api.dto.UserResponseDto;
import com.anaeltech.bookea_api.dto.UserUpdateDto;
import com.anaeltech.bookea_api.entity.User;
import com.anaeltech.bookea_api.service.UserService;

@Service
public class UserServiceImplement implements UserService {

  @Override
  public Page<UserResponseDto> findAll(Pageable pageable) {
  }

  @Override
  public UserResponseDto findById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public UserResponseDto createUser(UserCreateDto user) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createUser'");
  }

  @Override
  public UserResponseDto findByEmail(String email) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
  }

  @Override
  public void deleteUser(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
  }

  @Override
  public UserResponseDto updateUser(UserUpdateDto user) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
  }
}
