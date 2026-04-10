package com.anaeltech.bookea_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anaeltech.bookea_api.entity.User;

public interface UserService {

  Page<User> findAll(Pageable pageable);

  User findById(Long id);

  User createUser(User user);

  User findByEmail(String email);

  void deleteUser(Long id);

  User updateUser(User user);

}
