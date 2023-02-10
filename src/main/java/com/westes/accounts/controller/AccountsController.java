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
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
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
//  @CircuitBreaker(name = "detailsForCustomerSupportApp", fallbackMethod = "customerDetailsFallback")
  @Retry(name = "retryForCustomerDetails", fallbackMethod = "customerDetailsFallback")
  public CustomerDetail getCustomerDetails(
      @RequestHeader("westes-correlation-id") String correlationId, @PathVariable int customerId) {
    var account = accountRepository.findAccountByCustomerId(customerId);
    var loans = loansFeignClient.getLoansDetails(correlationId, customerId);
    var cards = cardsFeignClient.getCardDetails(correlationId, customerId);

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
  private CustomerDetail customerDetailsFallback(String correlationId, int customerId,
      Throwable t) {
    var account = accountRepository.findAccountByCustomerId(customerId);
    var loans = loansFeignClient.getLoansDetails(correlationId, customerId);

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
