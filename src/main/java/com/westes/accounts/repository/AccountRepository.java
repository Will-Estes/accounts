package com.westes.accounts.repository;

import com.westes.accounts.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
  Account findAccountByCustomerId(int customerId);
}
