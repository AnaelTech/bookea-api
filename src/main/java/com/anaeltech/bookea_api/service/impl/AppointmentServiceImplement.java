package com.anaeltech.bookea_api.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.anaeltech.bookea_api.dto.AppointmentCreateDto;
import com.anaeltech.bookea_api.dto.AppointmentResponseDto;
import com.anaeltech.bookea_api.dto.AppointmentUpdateDto;
import com.anaeltech.bookea_api.entity.Appointment;
import com.anaeltech.bookea_api.entity.Client;
import com.anaeltech.bookea_api.entity.User;
import com.anaeltech.bookea_api.exceptions.AppointmentAlreadyReservedException;
import com.anaeltech.bookea_api.exceptions.AppointmentNotFoundException;
import com.anaeltech.bookea_api.exceptions.UserNotFoundException;
import com.anaeltech.bookea_api.mapper.AppointmentMapper;
import com.anaeltech.bookea_api.repository.AppointmentRepository;
import com.anaeltech.bookea_api.repository.ClientRepository;
import com.anaeltech.bookea_api.repository.UserRepository;
import com.anaeltech.bookea_api.service.AppointmentService;

public class AppointmentServiceImplement implements AppointmentService {

  private final AppointmentRepository appointmentRepository;
  private final AppointmentMapper appointmentMapper;
  private final UserRepository userRepository;
  private final ClientRepository clientRepository;

  public AppointmentServiceImplement(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper,
      UserRepository userRepository, ClientRepository clientRepository) {
    this.appointmentRepository = appointmentRepository;
    this.appointmentMapper = appointmentMapper;
    this.userRepository = userRepository;
    this.clientRepository = clientRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AppointmentResponseDto> findAll(Pageable pageable) {
    return appointmentRepository.findAll(pageable).map(appointmentMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public AppointmentResponseDto findById(Long id) {
    return appointmentRepository.findById(id).map(appointmentMapper::toDto)
        .orElseThrow(() -> new AppointmentNotFoundException(id));
  }

  @Override
  @Transactional
  public AppointmentResponseDto createAppointment(AppointmentCreateDto appointmentCreateDto) {
    User user = userRepository.findById(appointmentCreateDto.userId())
        .orElseThrow(() -> new UserNotFoundException("User not found with id" + appointmentCreateDto.userId()));

    Client client = clientRepository.findById(appointmentCreateDto.clientId())
        .orElseThrow(() -> new UserNotFoundException("Client not found with id" + appointmentCreateDto.clientId()));

    if (appointmentRepository.hasConflictingAppointments(user, appointmentCreateDto.startAt(),
        appointmentCreateDto.endAt())) {
      throw new AppointmentAlreadyReservedException();
    }
    Appointment savedAppointment = appointmentRepository
        .save(appointmentMapper.toEntity(appointmentCreateDto, user, client));
    return appointmentMapper.toDto(savedAppointment);
  }

  @Override
  @Transactional
  public void deleteAppointment(Long id) {
    if (!appointmentRepository.existsById(id)) {
      throw new AppointmentNotFoundException(id);
    }
    appointmentRepository.deleteById(id);
  }

  @Override
  @Transactional
  public AppointmentResponseDto updateAppointment(Long id, AppointmentUpdateDto appointmentUpdateDto) {
    Appointment appointment = appointmentRepository.findById(id)
        .orElseThrow(() -> new AppointmentNotFoundException(id));

    appointmentMapper.updateEntity(appointmentUpdateDto, appointment);
    Appointment updatedAppointment = appointmentRepository.save(appointment);
    return appointmentMapper.toDto(updatedAppointment);
  }
}
