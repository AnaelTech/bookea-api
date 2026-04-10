package com.anaeltech.bookea_api.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anaeltech.bookea_api.entity.Client;
import com.anaeltech.bookea_api.service.ClientService;

public class ClientServiceImplement implements ClientService {

  @Override
  public Page<Client> findAll(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public Client findById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public Client createClient(Client client) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createClient'");
  }

  @Override
  public void deleteClient(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteClient'");
  }

  @Override
  public Client updateClient(Client client) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateClient'");
  }

}
