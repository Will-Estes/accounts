package com.westes.accounts.model;

import java.util.List;
import lombok.Data;

@Data
public class CustomerDetail {
  private Account account;
  private List<Loan> loans;
  private List<Card> cards;
}
