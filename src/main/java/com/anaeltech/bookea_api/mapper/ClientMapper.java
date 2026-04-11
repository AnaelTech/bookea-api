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
    client.setFirstname(clientCreateDto.getFirstname());
    client.setLastname(clientCreateDto.getLastname());
    client.setEmail(clientCreateDto.getEmail());
    client.setPhone(clientCreateDto.getPhone());
    return client;
  }

  public void updateEntity(ClientUpdateDto clientUpdateDto, Client client) {
    client.setFirstname(clientUpdateDto.getFirstname());
    client.setLastname(clientUpdateDto.getLastname());
    client.setPhone(clientUpdateDto.getPhone());
  }
}
