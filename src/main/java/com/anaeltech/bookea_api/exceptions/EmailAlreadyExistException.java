package com.anaeltech.bookea_api.exceptions;

public class EmailAlreadyExistException extends RuntimeException {
  public EmailAlreadyExistException(String email) {
    super("Email already exists: " + email);
  }
}
