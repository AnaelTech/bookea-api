package com.anaeltech.bookea_api.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anaeltech.bookea_api.dto.UserCreateDto;
import com.anaeltech.bookea_api.dto.UserResponseDto;
import com.anaeltech.bookea_api.dto.UserUpdateDto;
import com.anaeltech.bookea_api.entity.User;
import com.anaeltech.bookea_api.exceptions.EmailAlreadyExistException;
import com.anaeltech.bookea_api.exceptions.IsNotAnEmailException;
import com.anaeltech.bookea_api.exceptions.UserNotFoundException;
import com.anaeltech.bookea_api.mapper.UserMapper;
import com.anaeltech.bookea_api.repository.UserRepository;
import com.anaeltech.bookea_api.service.UserService;
import com.anaeltech.bookea_api.utils.CheckIfEmail;

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
  @Transactional(readOnly = true)
  public Page<UserResponseDto> findAll(Pageable pageable) {
    return userRepository.findAll(pageable).map(userMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public UserResponseDto findById(Long id) {
    return userRepository.findById(id).map(userMapper::toDto)
        .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
  }

  @Override
  @Transactional
  public UserResponseDto createUser(UserCreateDto userCreateDto) {
    if (userRepository.existsByEmail(userCreateDto.email())) {
      throw new EmailAlreadyExistException(userCreateDto.email());
    }
    String encodedPassword = passwordEncoder.encode(userCreateDto.password());
    User savedUser = userRepository.save(userMapper.toEntity(userCreateDto, encodedPassword));
    return userMapper.toDto(savedUser);
  }

  @Override
  @Transactional(readOnly = true)
  public UserResponseDto findByEmail(String email) {
    if (!CheckIfEmail.validate(email)) {
      throw new IsNotAnEmailException(email);
    }
    return userRepository.findByEmail(email).map(userMapper::toDto)
        .orElseThrow(() -> new UserNotFoundException("User not found with email " + email));
  }

  @Override
  @Transactional
  public void deleteUser(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    userRepository.delete(user);
  }

  @Override
  @Transactional
  public UserResponseDto updateUser(Long id, UserUpdateDto userUpdateDto) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

    userMapper.updateEntity(userUpdateDto, user);
    return userMapper.toDto(user);
  }
}
