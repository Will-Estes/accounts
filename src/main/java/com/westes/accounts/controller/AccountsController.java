package com.westes.accounts.controller;

import com.westes.accounts.model.Account;
import com.westes.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountsController {

  private final AccountRepository accountRepository;

  @GetMapping("/accounts/{customerId}")
  public Account getAccountDetails(@PathVariable int customerId) {
    var account = accountRepository.findAccountByCustomerId(customerId);
    return account;
  }

}
