package com.example.fintech.fintechbackend.controller;

import com.example.fintech.fintechbackend.service.AccountService;
import com.example.fintech.fintechbackend.service.AuthService;
import com.example.fintech.fintechbackend.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Tag(name = "Test", description = "Test and maintenance endpoints")
public class TestController {

  private final AuthService authService;
  private final AccountService accountService;
  private final TransactionService transactionService;

  public TestController(AuthService authService, AccountService accountService, TransactionService transactionService) {
    this.authService = authService;
    this.accountService = accountService;
    this.transactionService = transactionService;
  }

  @PostMapping("/reset")
  @Operation(summary = "Reset all data", description = "Reset users, accounts, and transactions (test only).")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Reset completed")
  })
  public String resetAll() {
    authService.reset();
    accountService.reset();
    transactionService.reset();

    return "RESET_OK";
  }

}
