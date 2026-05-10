package com.anaeltech.bookea_api.mapper;

import org.springframework.stereotype.Component;

import com.anaeltech.bookea_api.dto.ClientCreateDto;
import com.anaeltech.bookea_api.dto.ClientResponseDto;
import com.anaeltech.bookea_api.dto.ClientUpdateDto;
import com.anaeltech.bookea_api.entity.Client;

@Component
public class ClientMapper {

  public ClientResponseDto toDto(Client client) {
    return new ClientResponseDto(client.getId(), client.getFirstname(), client.getLastname(), client.getEmail(),
        client.getPhone(), client.getNotes());
  }

  public Client toEntity(ClientCreateDto clientCreateDto) {
    Client client = new Client();
    client.setFirstname(clientCreateDto.firstname());
    client.setLastname(clientCreateDto.lastname());
    client.setEmail(clientCreateDto.email());
    client.setPhone(clientCreateDto.phone());
    return client;
  }

  public void updateEntity(ClientUpdateDto clientUpdateDto, Client client) {
    client.setFirstname(clientUpdateDto.firstname());
    client.setLastname(clientUpdateDto.lastname());
    client.setPhone(clientUpdateDto.phone());
  }
}
