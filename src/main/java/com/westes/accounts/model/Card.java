package com.westes.accounts.model;

import java.sql.Date;
import lombok.Data;

@Data
public class Card {

  private int cardId;
  private int customerId;
  private String cardNumber;
  private String cardType;
  private int totalLimit;
  private int amountUsed;
  private int availableAmount;
  private Date createDt;
}
