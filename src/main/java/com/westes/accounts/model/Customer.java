package com.westes.accounts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Table(name = "customer")
@Data
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "customer_id")
  private int customerId;
  @Column(name = "name")
  private String name;
  @Column(name = "email")
  private String email;
  @Column(name = "mobile_number")
  private String mobileNumber;
  @Column(name = "create_dt")
  private LocalDate createDt;

}