package com.anaeltech.bookea_api.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.anaeltech.bookea_api.dto.CancellationStatsDto;
import com.anaeltech.bookea_api.dto.ClientStatsDto;
import com.anaeltech.bookea_api.dto.OccupancyStatsDto;
import com.anaeltech.bookea_api.dto.StatsOverviewDto;
import com.anaeltech.bookea_api.entity.Appointment;
import com.anaeltech.bookea_api.entity.AppointmentStatus;
import com.anaeltech.bookea_api.exceptions.UserNotFoundException;
import com.anaeltech.bookea_api.repository.AppointmentRepository;
import com.anaeltech.bookea_api.repository.ClientRepository;
import com.anaeltech.bookea_api.repository.UserRepository;
import com.anaeltech.bookea_api.service.StatsService;

@Service
public class StatsServiceImplement implements StatsService {

  private final AppointmentRepository appointmentRepository;
  private final ClientRepository clientRepository;
  private final UserRepository userRepository;

  public StatsServiceImplement(AppointmentRepository appointmentRepository, ClientRepository clientRepository,
      UserRepository userRepository) {
    this.appointmentRepository = appointmentRepository;
    this.clientRepository = clientRepository;
    this.userRepository = userRepository;

  }

  @Override
  public StatsOverviewDto getStatsOverview(Long userId, LocalDateTime start, LocalDateTime end) {
    valideInputs(userId, start, end);
    OccupancyStatsDto occupancy = getOccupancyStats(userId, start, end);
    CancellationStatsDto cancellation = getCancellationStats(userId, start, end);
    ClientStatsDto clients = getClientStats(userId, start, end);

    long totalAppointments = appointmentRepository.findByUserAndStartAtBetween(userId, start, end).size();

    return new StatsOverviewDto(occupancy.fillRate(), totalAppointments, cancellation.cancelled(),
        cancellation.noShow(), clients.totalClients(), clients.newClients());
  }

  @Override
  public OccupancyStatsDto getOccupancyStats(Long userId, LocalDateTime start, LocalDateTime end) {
    valideInputs(userId, start, end);
    List<Appointment> appointments = appointmentRepository.findByUserAndStartAtBetween(userId, start, end);

    // 🔥 1. Calcul temps réservé
    long bookedMinutes = appointments.stream()
        .filter(a -> a.getStatus() == AppointmentStatus.SCHEDULED
            || a.getStatus() == AppointmentStatus.COMPLETED)
        .mapToLong(a -> Duration.between(a.getStartAt(), a.getEndAt()).toMinutes())
        .sum();

    // 🔥 2. Temps total disponible
    long availableMinutes = Duration.between(start, end).toMinutes();

    // 🔥 3. Temps libre
    long freeMinutes = Math.max(0, availableMinutes - bookedMinutes);

    // 🔥 4. Taux de remplissage
    double fillRate = availableMinutes == 0
        ? 0
        : (double) bookedMinutes / availableMinutes;

    return new OccupancyStatsDto(
        availableMinutes,
        bookedMinutes,
        freeMinutes,
        fillRate);
  }

  @Override
  public CancellationStatsDto getCancellationStats(Long userId, LocalDateTime start, LocalDateTime end) {
    valideInputs(userId, start, end);
    List<Appointment> appointments = appointmentRepository.findByUserAndStartAtBetween(userId, start, end);

    long total = appointments.size();

    long cancelled = appointments.stream()
        .filter(a -> a.getStatus() == AppointmentStatus.CANCELLED)
        .count();

    long noShow = appointments.stream()
        .filter(a -> a.getStatus() == AppointmentStatus.NO_SHOW)
        .count();

    double cancellationRate = total == 0 ? 0 : (double) cancelled / total;
    double noShowRate = total == 0 ? 0 : (double) noShow / total;

    return new CancellationStatsDto(
        total,
        cancelled,
        noShow,
        cancellationRate,
        noShowRate);
  }

  @Override
  public ClientStatsDto getClientStats(Long userId, LocalDateTime start, LocalDateTime end) {
    valideInputs(userId, start, end);
    long totalClients = clientRepository.countById(userId);

    long newClients = clientRepository.countByIdAndCreatedAtBetween(userId, start, end);

    // 👉 version simple MVP (on améliore plus tard)
    long returningClients = totalClients - newClients;

    return new ClientStatsDto(
        totalClients,
        newClients,
        Math.max(0, returningClients));
  }

  private void valideInputs(Long userId, LocalDateTime start, LocalDateTime end) {
    if (userId == null) {
      throw new IllegalArgumentException("UserId must not be null");
    }
    if (start == null || end == null) {
      throw new IllegalArgumentException("Start and end must not be null");
    }
    if (start.isAfter(end)) {
      throw new IllegalArgumentException("Start must not be after end");
    }
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException("User with id " + userId + " not found");
    }
  }
}
