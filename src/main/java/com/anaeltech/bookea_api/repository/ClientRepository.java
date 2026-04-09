package com.anaeltech.bookea_api.repository;

import com.anaeltech.bookea_api.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
  Client findByEmail(String email);
}
