package com.westes.accounts.controller;

import com.westes.accounts.config.AccountsServiceConfig;
import com.westes.accounts.model.Account;
import com.westes.accounts.model.CustomerDetail;
import com.westes.accounts.model.Properties;
import com.westes.accounts.repository.AccountRepository;
import com.westes.accounts.service.client.CardsFeignClient;
import com.westes.accounts.service.client.LoansFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountsController {

  private final AccountsServiceConfig accountsConfig;
  private final AccountRepository accountRepository;
  private final CardsFeignClient cardsFeignClient;
  private final LoansFeignClient loansFeignClient;

  @GetMapping("/accounts/{customerId}")
  public Account getAccountDetails(@PathVariable int customerId) {
    return accountRepository.findAccountByCustomerId(customerId);
  }

  @GetMapping("/customer-details/{customerId}")
  public CustomerDetail getCustomerDetails(@PathVariable int customerId) {
    var account = accountRepository.findAccountByCustomerId(customerId);
    var loans = loansFeignClient.getLoansDetails(customerId);
    var cards = cardsFeignClient.getCardDetails(customerId);

    CustomerDetail customerDetail = new CustomerDetail();
    customerDetail.setAccount(account);
    customerDetail.setLoans(loans);
    customerDetail.setCards(cards);

    return customerDetail;
  }

  @GetMapping("/accounts/properties")
  public Properties getPropertyDetails() {
    return new Properties(accountsConfig.getMsg(),
        accountsConfig.getBuildVersion(),
        accountsConfig.getMailDetails(), accountsConfig.getActiveBranches());
  }

}
