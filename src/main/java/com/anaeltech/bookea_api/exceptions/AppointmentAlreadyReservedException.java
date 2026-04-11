package com.anaeltech.bookea_api.exceptions;

public class AppointmentAlreadyReservedException extends RuntimeException {
  public AppointmentAlreadyReservedException() {
    super("Appointment already reserved");
  }
}
