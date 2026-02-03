package com.example.fintech.fintechbackend.controller;

import com.example.fintech.fintechbackend.exception.FintechException;
import com.example.fintech.fintechbackend.model.Account;
import com.example.fintech.fintechbackend.model.ErrorCode;
import com.example.fintech.fintechbackend.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("/{accountId}")
  public Account getAccount(@PathVariable String accountId) {
    return accountService.getAccount(accountId);
  }

  @PostMapping("/{accountId}/fund")
  public Account fundAccount(@PathVariable String accountId, @RequestBody Map<String, String> body) {
    String amountValue = body.get("amount");

    BigDecimal amount;
    try {
      amount = new BigDecimal(amountValue);
    } catch (NumberFormatException e) {
      throw new FintechException(ErrorCode.INVALID_AMOUNT);
    }

    return accountService.deposit(accountId, amount);
  }
}