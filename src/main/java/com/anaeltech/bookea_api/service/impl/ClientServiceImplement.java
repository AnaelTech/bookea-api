package com.anaeltech.bookea_api.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anaeltech.bookea_api.dto.ClientCreateDto;
import com.anaeltech.bookea_api.dto.ClientResponseDto;
import com.anaeltech.bookea_api.dto.ClientUpdateDto;
import com.anaeltech.bookea_api.entity.Client;
import com.anaeltech.bookea_api.service.ClientService;

public class ClientServiceImplement implements ClientService {

  @Override
  public Page<ClientResponseDto> findAll(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public ClientResponseDto findById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public ClientResponseDto createClient(ClientCreateDto clientCreateDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createClient'");
  }

  @Override
  public void deleteClient(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteClient'");
  }

  @Override
  public ClientResponseDto updateClient(Long id, ClientUpdateDto clientUpdateDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateClient'");
  }

}
