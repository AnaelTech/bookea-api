package com.anaeltech.bookea_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anaeltech.bookea_api.entity.Client;

public interface ClientService {

  Page<Client> findAll(Pageable pageable);

  Client findById(Long id);

  Client createClient(Client client);

  void deleteClient(Long id);

  Client updateClient(Client client);

}
