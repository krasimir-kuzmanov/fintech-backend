package com.example.fintech.fintechbackend.controller;

import com.example.fintech.fintechbackend.exception.FintechException;
import com.example.fintech.fintechbackend.model.ErrorCode;
import com.example.fintech.fintechbackend.model.Transaction;
import com.example.fintech.fintechbackend.service.TransactionService;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Payments and transaction history")
public class TransactionController {

  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping("/payment")
  @Operation(summary = "Make payment", description = "Transfer funds from one account to another.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Payment successful"),
      @ApiResponse(responseCode = "400", description = "Invalid amount or request"),
      @ApiResponse(responseCode = "404", description = "Account not found")
  })
  public Transaction makePayment(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Payment request payload",
          required = true,
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = Map.class),
              examples = @ExampleObject(value = "{\"fromAccountId\": \"acc-1\", \"toAccountId\": \"acc-2\", \"amount\": \"25.50\"}")
          )
      )
      @RequestBody Map<String, String> body
  ) {
    String fromAccountId = body.get("fromAccountId");
    String toAccountId = body.get("toAccountId");
    String amountValue = body.get("amount");

    BigDecimal amount;
    try {
      amount = new BigDecimal(amountValue);
    } catch (NumberFormatException e) {
      throw new FintechException(ErrorCode.INVALID_AMOUNT);
    }

    return transactionService.makePayment(fromAccountId, toAccountId, amount);
  }

  @GetMapping("/{accountId}")
  @Operation(summary = "Get transactions", description = "List transactions for an account.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Transactions retrieved"),
      @ApiResponse(responseCode = "404", description = "Account not found")
  })
  public List<Transaction> getTransactions(
      @Parameter(description = "Account identifier", required = true)
      @PathVariable String accountId
  ) {
    return transactionService.getTransactions(accountId);
  }
}
