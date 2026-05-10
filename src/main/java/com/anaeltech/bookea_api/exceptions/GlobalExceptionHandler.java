package com.anaeltech.bookea_api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.anaeltech.bookea_api.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFound(
      UserNotFoundException ex,
      HttpServletRequest request) {

    ErrorResponse error = new ErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        "User not found",
        ex.getMessage(),
        request.getRequestURI(),
        "USER_NOT_FOUND");

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}
