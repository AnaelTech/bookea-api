package com.anaeltech.bookea_api.exceptions;

public class IsNotAnEmailException extends RuntimeException {

  public IsNotAnEmailException(String email) {

    super("Email is not an email: " + email);

  }

}
