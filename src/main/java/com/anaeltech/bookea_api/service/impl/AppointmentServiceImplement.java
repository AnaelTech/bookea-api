package com.anaeltech.bookea_api.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.anaeltech.bookea_api.entity.Appointment;
import com.anaeltech.bookea_api.service.AppointmentService;

public class AppointmentServiceImplement implements AppointmentService {

  @Override
  public Page<Appointment> findAll(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  @Override
  public Appointment findById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findById'");
  }

  @Override
  public Appointment createAppointment(Appointment appointment) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'createAppointment'");
  }

  @Override
  public void deleteAppointment(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteAppointment'");
  }

  @Override
  public Appointment updateAppointment(Appointment appointment) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateAppointment'");
  }
}
