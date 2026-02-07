package com.example.fintech.fintechbackend.controller;

import com.example.fintech.fintechbackend.exception.FintechException;
import com.example.fintech.fintechbackend.model.Account;
import com.example.fintech.fintechbackend.model.ErrorCode;
import com.example.fintech.fintechbackend.service.AccountService;
import com.example.fintech.fintechbackend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/account")
@Tag(name = "Account", description = "Account operations")
public class AccountController {

  private final AccountService accountService;
  private final AuthService authService;

  public AccountController(AccountService accountService, AuthService authService) {
    this.accountService = accountService;
    this.authService = authService;
  }

  @GetMapping("/{accountId}")
  @Operation(summary = "Get account", description = "Fetch account details by account ID.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Account found"),
      @ApiResponse(responseCode = "404", description = "Account not found")
  })
  public Account getAccount(
      @Parameter(description = "Account identifier", required = true)
      @PathVariable String accountId,
      @RequestHeader(value = "Authorization", required = false) String authorizationHeader
  ) {
    authorizeAccount(authorizationHeader, accountId);
    return accountService.getAccount(accountId);
  }

  @PostMapping("/{accountId}/fund")
  @Operation(summary = "Fund account", description = "Deposit funds into an account.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Account funded"),
      @ApiResponse(responseCode = "400", description = "Invalid amount"),
      @ApiResponse(responseCode = "404", description = "Account not found")
  })
  public Account fundAccount(
      @Parameter(description = "Account identifier", required = true)
      @PathVariable String accountId,
      @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Funding request payload",
          required = true,
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = Map.class),
              examples = @ExampleObject(value = "{\"amount\": \"100.00\"}")
          )
      )
      @RequestBody Map<String, String> body
  ) {
    authorizeAccount(authorizationHeader, accountId);

    String amountValue = body.get("amount");

    BigDecimal amount;
    try {
      amount = new BigDecimal(amountValue);
    } catch (NumberFormatException e) {
      throw new FintechException(ErrorCode.INVALID_AMOUNT);
    }

    return accountService.deposit(accountId, amount);
  }

  private void authorizeAccount(String authorizationHeader, String accountId) {
    String userId = authService.authenticate(authorizationHeader);
    if (!userId.equals(accountId)) {
      throw new FintechException(ErrorCode.FORBIDDEN);
    }
  }
}
