package com.westes.accounts.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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