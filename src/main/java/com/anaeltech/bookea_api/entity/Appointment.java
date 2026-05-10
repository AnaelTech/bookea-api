package com.anaeltech.bookea_api.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "appointments", indexes = { @Index(name = "idx_appointment_user", columnList = "user_id"),
    @Index(name = "idx_appointment_client", columnList = "client_id"),
    @Index(name = "idx_appointment_start", columnList = "start_at") })
public class Appointment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;

  @NotNull
  @Column(name = "start_at", nullable = false)
  private LocalDateTime startAt;

  @NotNull
  @Column(name = "end_at", nullable = false)
  private LocalDateTime endAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", length = 20, nullable = false)
  private AppointmentStatus status;

  @Column(name = "notes", length = 500)
  private String notes;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  public Appointment() {
  }

  public Appointment(User user, Client client, LocalDateTime startAt, LocalDateTime endAt) {
    this.user = user;
    this.client = client;
    this.startAt = startAt;
    this.endAt = endAt;
  }

  public Appointment(User user, Client client, @NotNull LocalDateTime startAt, @NotNull LocalDateTime endAt,
      AppointmentStatus status) {
    this.user = user;
    this.client = client;
    this.startAt = startAt;
    this.endAt = endAt;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public LocalDateTime getStartAt() {
    return startAt;
  }

  public void setStartAt(LocalDateTime startAt) {
    this.startAt = startAt;
  }

  public LocalDateTime getEndAt() {
    return endAt;
  }

  public void setEndAt(LocalDateTime endAt) {
    this.endAt = endAt;
  }

  public AppointmentStatus getStatus() {
    return status;
  }

  public void setStatus(AppointmentStatus status) {
    this.status = status;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

}
