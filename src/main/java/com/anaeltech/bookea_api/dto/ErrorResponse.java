package com.anaeltech.bookea_api.dto;

import java.time.Instant;

public record ErrorResponse(int status, String title, String detail, String path, String errorCode, Instant timestamp) {

  public ErrorResponse(int status, String title, String detail, String path, String errorCode) {
    this(status, title, detail, path, errorCode, Instant.now());
  }

}
