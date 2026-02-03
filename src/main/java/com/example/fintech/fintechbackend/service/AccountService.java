package com.example.fintech.fintechbackend.service;

import com.example.fintech.fintechbackend.exception.FintechException;
import com.example.fintech.fintechbackend.model.Account;
import com.example.fintech.fintechbackend.model.ErrorCode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
  private final Map<String, Account> accounts = new HashMap<>();

  public Account getAccount(String accountId) {
    return accounts.computeIfAbsent(accountId, id -> new Account(id, BigDecimal.ZERO));
  }

  public Account deposit(String accountId, BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new FintechException(ErrorCode.INVALID_AMOUNT);
    }

    Account account = getAccount(accountId);
    account.setBalance(account.getBalance().add(amount));
    accounts.put(accountId, account);

    return account;
  }

  public void reset() {
    accounts.clear();
  }
}