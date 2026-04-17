package com.anaeltech.bookea_api.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anaeltech.bookea_api.dto.ClientCreateDto;
import com.anaeltech.bookea_api.dto.ClientResponseDto;
import com.anaeltech.bookea_api.dto.ClientUpdateDto;
import com.anaeltech.bookea_api.entity.Client;
import com.anaeltech.bookea_api.exceptions.EmailAlreadyExistException;
import com.anaeltech.bookea_api.exceptions.UserNotFoundException;
import com.anaeltech.bookea_api.mapper.ClientMapper;
import com.anaeltech.bookea_api.repository.ClientRepository;
import com.anaeltech.bookea_api.service.ClientService;

@Service
public class ClientServiceImplement implements ClientService {

  private ClientRepository clientRepository;
  private ClientMapper clientMapper;

  public ClientServiceImplement(ClientRepository clientRepository, ClientMapper clientMapper) {
    this.clientRepository = clientRepository;
    this.clientMapper = clientMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ClientResponseDto> findAll(Pageable pageable) {
    return clientRepository.findAll(pageable).map(clientMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public ClientResponseDto findById(Long id) {
    return clientRepository.findById(id).map(clientMapper::toDto)
        .orElseThrow(() -> new UserNotFoundException("Client not found with id " + id));
  }

  @Override
  @Transactional
  public ClientResponseDto createClient(ClientCreateDto clientCreateDto) {
    if (clientRepository.existsByEmail(clientCreateDto.email())) {
      throw new EmailAlreadyExistException(clientCreateDto.email());
    }
    Client savedClient = clientRepository.save(clientMapper.toEntity(clientCreateDto));
    return clientMapper.toDto(savedClient);
  }

  @Override
  @Transactional
  public void deleteClient(Long id) {
    if (!clientRepository.existsById(id)) {
      throw new UserNotFoundException("Client not found with id " + id);
    }
    clientRepository.deleteById(id);
  }

  @Override
  @Transactional
  public ClientResponseDto updateClient(Long id, ClientUpdateDto clientUpdateDto) {
    Client client = clientRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("Client not found with id " + id));

    clientMapper.updateEntity(clientUpdateDto, client);
    return clientMapper.toDto(client);
  }

  @Override
  @Transactional(readOnly = true)
  public ClientResponseDto findByEmail(String email) {
    return clientRepository.findByEmail(email).map(clientMapper::toDto)
        .orElseThrow(() -> new UserNotFoundException("Client not found with email " + email));
  }

}
