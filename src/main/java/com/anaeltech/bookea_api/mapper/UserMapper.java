package com.anaeltech.bookea_api.mapper;

import org.springframework.stereotype.Component;

import com.anaeltech.bookea_api.dto.UserCreateDto;
import com.anaeltech.bookea_api.dto.UserResponseDto;
import com.anaeltech.bookea_api.dto.UserUpdateDto;
import com.anaeltech.bookea_api.entity.User;

@Component
public class UserMapper {

  public UserResponseDto toDto(User user) {
    return new UserResponseDto(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getPhone());
  }

  public User toEntity(UserCreateDto userCreateDto) {
    User user = new User();
    user.setFirstname(userCreateDto.getFirstname());
    user.setLastname(userCreateDto.getLastname());
    user.setEmail(userCreateDto.getEmail());
    user.setPhone(userCreateDto.getPhone());
    return user;
  }

  public void updateEntity(UserUpdateDto userUpdateDto, User user) {
    user.setFirstname(userUpdateDto.getFirstname());
    user.setLastname(userUpdateDto.getLastname());
    user.setPhone(userUpdateDto.getPhone());
  }
}
