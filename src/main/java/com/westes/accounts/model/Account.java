package com.westes.accounts.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "account")
@Data
public class Account {

  @Id
  @Column(name = "account_number")
  private long accountNumber;
  @Column(name = "customer_id")
  private int customerId;
  @Column(name = "account_type")
  private String accountType;
  @Column(name = "branch_address")
  private String branchAddress;
  @Column(name = "create_dt")
  private LocalDate createDt;

}
