package com.anaeltech.bookea_api.exceptions;

public class InvalidCredentialsException extends RuntimeException {

  public InvalidCredentialsException() {
    super("Invalid credentials");
  }

}
