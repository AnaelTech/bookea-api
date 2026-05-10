package com.anaeltech.bookea_api.exceptions;

public class ClientNotFoundException extends RuntimeException {
  public ClientNotFoundException(Long id) {
    super("Client not found with id " + id);
  }

  public ClientNotFoundException(String email) {
    super("Client not found with email " + email);
  }
}
