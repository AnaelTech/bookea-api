package com.anaeltech.bookea_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anaeltech.bookea_api.dto.ClientCreateDto;
import com.anaeltech.bookea_api.dto.ClientResponseDto;
import com.anaeltech.bookea_api.dto.ClientUpdateDto;

public interface ClientService {

  Page<ClientResponseDto> findAll(Pageable pageable);

  ClientResponseDto findById(Long id);

  ClientResponseDto createClient(ClientCreateDto client);

  void deleteClient(Long id);

  ClientResponseDto updateClient(Long id, ClientUpdateDto client);

  ClientResponseDto findByEmail(String email);
}
