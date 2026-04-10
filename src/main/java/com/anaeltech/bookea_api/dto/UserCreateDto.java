package com.anaeltech.bookea_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCreateDto {

  @NotBlank
  private String firstname;

  @NotBlank
  private String lastname;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Size(min = 8, max = 255)
  private String password;

  @NotBlank
  @Size(max = 20)
  private String phone;

  public UserCreateDto(String firstname, String lastname, String email, String password, String phone) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
    this.phone = phone;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

}
