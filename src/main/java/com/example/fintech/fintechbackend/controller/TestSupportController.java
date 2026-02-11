package com.example.fintech.fintechbackend.controller;

import com.example.fintech.fintechbackend.model.User;
import com.example.fintech.fintechbackend.service.AccountService;
import com.example.fintech.fintechbackend.service.AuthService;
import com.example.fintech.fintechbackend.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/users/{username}")
  public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
    return authService.findByUsername(username)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
