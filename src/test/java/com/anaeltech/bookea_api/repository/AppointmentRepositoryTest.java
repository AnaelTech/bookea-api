package com.anaeltech.bookea_api.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.anaeltech.bookea_api.entity.Appointment;
import com.anaeltech.bookea_api.entity.AppointmentStatus;
import com.anaeltech.bookea_api.entity.Client;
import com.anaeltech.bookea_api.entity.User;

@DataJpaTest(showSql = true)
class AppointmentRepositoryTest {

  @Autowired
  private AppointmentRepository appointmentRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Test
  void shouldDetectConflict() {
    User user = new User();
    user.setEmail("john.doe@example.com");
    user.setPassword("123456789");
    user.setFirstname("John");
    user.setLastname("Doe");
    userRepository.save(user);

    Client client = new Client();
    client.setEmail("jane.doe@example.com");
    client.setLastname("Doe");
    client.setFirstname("Jane");
    clientRepository.save(client);

    Appointment existingAppointment = new Appointment(user, client, LocalDateTime.of(2026, 1, 1, 10, 0),
        LocalDateTime.of(2026, 1, 1, 11, 0), AppointmentStatus.SCHEDULED);

    appointmentRepository.save(existingAppointment);

    boolean conflict = appointmentRepository.hasConflictingAppointments(user,
        LocalDateTime.of(2026, 1, 1, 10, 30), LocalDateTime.of(2026, 1, 1, 11, 30));

    assertFalse(conflict);
  }

}
