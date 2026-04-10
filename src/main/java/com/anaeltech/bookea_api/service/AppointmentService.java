package com.anaeltech.bookea_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anaeltech.bookea_api.entity.Appointment;

public interface AppointmentService {

  Page<Appointment> findAll(Pageable pageable);

  Appointment findById(Long id);

  Appointment createAppointment(Appointment appointment);

  void deleteAppointment(Long id);

  Appointment updateAppointment(Appointment appointment);
}
