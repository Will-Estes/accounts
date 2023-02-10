package com.westes.accounts.service.client;

import com.westes.accounts.model.Loan;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("loans-service")
public interface LoansFeignClient {

  @GetMapping("/loans/{customerId}")
  List<Loan> getLoansDetails(@PathVariable int customerId);

}
