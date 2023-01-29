package com.westes.accounts.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
