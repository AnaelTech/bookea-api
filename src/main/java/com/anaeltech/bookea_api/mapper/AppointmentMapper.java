package com.anaeltech.bookea_api.mapper;

import org.springframework.stereotype.Component;

import com.anaeltech.bookea_api.dto.AppointmentCreateDto;
import com.anaeltech.bookea_api.dto.AppointmentResponseDto;
import com.anaeltech.bookea_api.dto.AppointmentUpdateDto;
import com.anaeltech.bookea_api.entity.Appointment;
import com.anaeltech.bookea_api.entity.Client;
import com.anaeltech.bookea_api.entity.User;

@Component
public class AppointmentMapper {

  private final UserMapper userMapper;
  private final ClientMapper clientMapper;

  public AppointmentMapper(UserMapper userMapper, ClientMapper clientMapper) {
    this.userMapper = userMapper;
    this.clientMapper = clientMapper;
  }

  public AppointmentResponseDto toDto(Appointment appointment) {
    return new AppointmentResponseDto(appointment.getId(), userMapper.toDto(appointment.getUser()),
        clientMapper.toDto(appointment.getClient()),
        appointment.getStartAt(), appointment.getEndAt(), appointment.getStatus(), appointment.getNotes());
  }

  public Appointment toEntity(AppointmentCreateDto appointmentCreateDto, User user, Client client) {
    Appointment appointment = new Appointment();
    appointment.setUser(user);
    appointment.setClient(client);
    appointment.setStartAt(appointmentCreateDto.startAt());
    appointment.setEndAt(appointmentCreateDto.endAt());
    appointment.setStatus(appointmentCreateDto.status());
    appointment.setNotes(appointmentCreateDto.notes());
    return appointment;
  }

  public void updateEntity(AppointmentUpdateDto appointmentUpdateDto, Appointment appointment) {
    appointment.setStartAt(appointmentUpdateDto.startAt());
    appointment.setEndAt(appointmentUpdateDto.endAt());
    appointment.setStatus(appointmentUpdateDto.status());
    appointment.setNotes(appointmentUpdateDto.notes());
  }

}
