package com.example.fintech.fintechbackend.service;

import com.example.fintech.fintechbackend.exception.FintechException;
import com.example.fintech.fintechbackend.model.Account;
import com.example.fintech.fintechbackend.model.ErrorCode;
import com.example.fintech.fintechbackend.model.Transaction;
import com.example.fintech.fintechbackend.model.TransactionStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TransactionService {

  private final Map<String, List<Transaction>> transactionsByAccount = new ConcurrentHashMap<>();
  private final AccountService accountService;

  public TransactionService(AccountService accountService) {
    this.accountService = accountService;
  }

  public Transaction makePayment(String fromAccountId, String toAccountId, BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new FintechException(ErrorCode.INVALID_AMOUNT);
    }

    if (fromAccountId.equals(toAccountId)) {
      throw new FintechException(ErrorCode.SAME_ACCOUNT_TRANSFER);
    }

    Account from = accountService.getAccount(fromAccountId);
    Account to = accountService.getAccount(toAccountId);

    if (from.getBalance().compareTo(amount) < 0) {
      throw new FintechException(ErrorCode.INSUFFICIENT_FUNDS);
    }

    from.setBalance(from.getBalance().subtract(amount));
    to.setBalance(to.getBalance().add(amount));

    Transaction transaction = new Transaction(
        UUID.randomUUID().toString(),
        fromAccountId,
        toAccountId,
        amount,
        TransactionStatus.SUCCESS
    );

    transactionsByAccount
        .computeIfAbsent(fromAccountId, k -> new CopyOnWriteArrayList<>())
        .add(transaction);
    transactionsByAccount
        .computeIfAbsent(toAccountId, k -> new CopyOnWriteArrayList<>())
        .add(transaction);

    return transaction;
  }

  public List<Transaction> getTransactions(String accountId) {
    return transactionsByAccount.getOrDefault(accountId, Collections.emptyList());
  }

  public void reset() {
    transactionsByAccount.clear();
  }
}
