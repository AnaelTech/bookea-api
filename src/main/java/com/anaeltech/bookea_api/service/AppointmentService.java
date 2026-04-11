package com.anaeltech.bookea_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anaeltech.bookea_api.dto.AppointmentCreateDto;
import com.anaeltech.bookea_api.dto.AppointmentResponseDto;
import com.anaeltech.bookea_api.dto.AppointmentUpdateDto;

public interface AppointmentService {

  Page<AppointmentResponseDto> findAll(Pageable pageable);

  AppointmentResponseDto findById(Long id);

  AppointmentResponseDto createAppointment(AppointmentCreateDto appointmentCreateDto);

  void deleteAppointment(Long id);

  AppointmentResponseDto updateAppointment(Long id, AppointmentUpdateDto appointmentUpdateDto);
}
