package com.westes.accounts.service.client;

import com.westes.accounts.model.Card;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("cards")
public interface CardsFeignClient {

  @GetMapping("/cards/{customerId}")
  List<Card> getCardDetails(@PathVariable int customerId);
}
