package com.anaeltech.bookea_api.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anaeltech.bookea_api.dto.UserCreateDto;
import com.anaeltech.bookea_api.dto.UserResponseDto;
import com.anaeltech.bookea_api.dto.UserUpdateDto;
import com.anaeltech.bookea_api.entity.User;
import com.anaeltech.bookea_api.exceptions.EmailAlreadyExistException;
import com.anaeltech.bookea_api.exceptions.UserNotFoundException;
import com.anaeltech.bookea_api.mapper.UserMapper;
import com.anaeltech.bookea_api.repository.UserRepository;
import com.anaeltech.bookea_api.service.UserService;

@Service
public class UserServiceImplement implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImplement(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Page<UserResponseDto> findAll(Pageable pageable) {
    return userRepository.findAll(pageable).map(userMapper::toDto);
  }

  @Override
  public UserResponseDto findById(Long id) {
    return userRepository.findById(id).map(userMapper::toDto)
        .orElseThrow(() -> new UserNotFoundException("User not found with id" + id));
  }

  @Override
  public UserResponseDto createUser(UserCreateDto userCreateDto) {
    if (userRepository.existsByEmail(userCreateDto.getEmail())) {
      throw new EmailAlreadyExistException(userCreateDto.getEmail());
    }
    String encodedPassword = passwordEncoder.encode(userCreateDto.getPassword());
    userCreateDto.setPassword(encodedPassword);
    User savedUser = userRepository.save(userMapper.toEntity(userCreateDto));
    return userMapper.toDto(savedUser);
  }

  @Override
  public UserResponseDto findByEmail(String email) {
    return userRepository.findByEmail(email).map(userMapper::toDto)
        .orElseThrow(() -> new UserNotFoundException("User not found with email" + email));
  }

  @Override
  public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
      throw new UserNotFoundException("User not found with id" + id);
    }
    userRepository.deleteById(id);
  }

  @Override
  public UserResponseDto updateUser(Long id, UserUpdateDto userUpdateDto) {
    User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id" + id));

    userMapper.updateEntity(userUpdateDto, user);
    User updatedUser = userRepository.save(user);
    return userMapper.toDto(updatedUser);
  }
}
