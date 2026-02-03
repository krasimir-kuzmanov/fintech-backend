package com.example.fintech.fintechbackend.controller;

import com.example.fintech.fintechbackend.exception.FintechException;
import com.example.fintech.fintechbackend.model.ErrorCode;
import com.example.fintech.fintechbackend.model.Transaction;
import com.example.fintech.fintechbackend.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping("/payment")
  public Transaction makePayment(@RequestBody Map<String, String> body) {
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
  public List<Transaction> getTransactions(@PathVariable String accountId) {
    return transactionService.getTransactions(accountId);
  }
}