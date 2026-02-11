package com.example.fintech.fintechbackend.controller;

import com.example.fintech.fintechbackend.service.AccountService;
import com.example.fintech.fintechbackend.service.AuthService;
import com.example.fintech.fintechbackend.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestSupportController {

  private final AuthService authService;
  private final AccountService accountService;
  private final TransactionService transactionService;

  public TestSupportController(
      AuthService authService,
      AccountService accountService,
      TransactionService transactionService
  ) {
    this.authService = authService;
    this.accountService = accountService;
    this.transactionService = transactionService;
  }

  @PostMapping("/reset")
  public ResponseEntity<Void> reset() {
    transactionService.reset();
    accountService.reset();
    authService.reset();

    return ResponseEntity.noContent().build();
  }
}
