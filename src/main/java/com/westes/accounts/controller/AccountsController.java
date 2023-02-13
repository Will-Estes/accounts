package com.westes.accounts.controller;

import com.westes.accounts.config.AccountsServiceConfig;
import com.westes.accounts.model.Account;
import com.westes.accounts.model.CustomerDetail;
import com.westes.accounts.model.Properties;
import com.westes.accounts.repository.AccountRepository;
import com.westes.accounts.service.client.CardsFeignClient;
import com.westes.accounts.service.client.LoansFeignClient;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountsController {

  private final AccountsServiceConfig accountsConfig;
  private final AccountRepository accountRepository;
  private final CardsFeignClient cardsFeignClient;
  private final LoansFeignClient loansFeignClient;

  @GetMapping("/accounts/{customerId}")
  @Timed(value = "getAccountDetails.time", description = "Time taken to get account details")
  public Account getAccountDetails(@PathVariable int customerId) {
    return accountRepository.findAccountByCustomerId(customerId);
  }

  @GetMapping("/customer-details/{customerId}")
  @Retry(name = "retryForCustomerDetails", fallbackMethod = "customerDetailsFallback")
  @Timed(value = "getCustomerDetails.time", description = "Time taken to get customer details")
  public CustomerDetail getCustomerDetails(@PathVariable int customerId) {
    log.info("getCustomerDetails started");
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

  // FallbackMethod
  private CustomerDetail customerDetailsFallback(int customerId, Throwable t) {
    var account = accountRepository.findAccountByCustomerId(customerId);
    var loans = loansFeignClient.getLoansDetails(customerId);

    CustomerDetail customerDetail = new CustomerDetail();
    customerDetail.setAccount(account);
    customerDetail.setLoans(loans);

    return customerDetail;
  }

  @GetMapping("/sayHello")
  @RateLimiter(name = "sayHello", fallbackMethod = "sayHelloFallback")
  public String sayHello() {
    return "Hello";
  }

  public String sayHelloFallback(Throwable t) {
    return "Hi there!";
  }

}
