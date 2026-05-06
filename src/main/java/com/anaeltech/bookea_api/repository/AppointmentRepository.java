package com.anaeltech.bookea_api.repository;

import com.anaeltech.bookea_api.entity.Appointment;
import com.anaeltech.bookea_api.entity.User;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

  List<Appointment> findByUser(User user);

  @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId AND a.startAt BETWEEN :start AND :end")
  List<Appointment> findByUserAndStartAtBetween(
      @Param("userId") Long userId,
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end);

  @Query("""
      SELECT COUNT(a) > 0 FROM Appointment a
      WHERE a.user = :user
      AND a.startAt < :endAt
      AND a.endAt > :startAt
      """)
  boolean hasConflictingAppointments(
      @Param("user") User user,
      @Param("startAt") LocalDateTime startAt,
      @Param("endAt") LocalDateTime endAt);

}
