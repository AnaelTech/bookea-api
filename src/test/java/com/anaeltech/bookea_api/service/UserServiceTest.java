package com.anaeltech.bookea_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.anaeltech.bookea_api.dto.UserResponseDto;
import com.anaeltech.bookea_api.entity.User;
import com.anaeltech.bookea_api.mapper.UserMapper;
import com.anaeltech.bookea_api.repository.UserRepository;
import com.anaeltech.bookea_api.service.impl.UserServiceImplement;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userMapper;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserServiceImplement userService;

  @Test
  void findAll_returnsPageOfUserResponseDto() {
    User user = new User();
    user.setId(1L);
    user.setEmail("john.doe@example.com");
    user.setFirstname("John");
    user.setLastname("Doe");
    user.setPhone("33758047768");

    Page<User> userPage = new PageImpl<>(List.of(user));
    Pageable pageable = PageRequest.of(0, 10);

    when(userRepository.findAll(pageable)).thenReturn(userPage);
    when(userMapper.toDto(user)).thenReturn(new UserResponseDto(1L, "John", "Doe", "john.doe@example.com", null));

    Page<UserResponseDto> result = userService.findAll(pageable);

    assertThat(result).isNotNull();
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getContent().get(0).email()).isEqualTo("john.doe@example.com");

  }

}
