package com.example.fintech.fintechbackend.controller;

import com.example.fintech.fintechbackend.model.User;
import com.example.fintech.fintechbackend.dto.CreateTestUserRequest;
import com.example.fintech.fintechbackend.service.AccountService;
import com.example.fintech.fintechbackend.service.AuthService;
import com.example.fintech.fintechbackend.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping("/users")
  @Operation(summary = "Create test user", description = "Create or replace a user (test only).")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "User created"),
      @ApiResponse(responseCode = "400", description = "Invalid request")
  })
  public User createTestUser(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Create test user payload",
          required = true,
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = CreateTestUserRequest.class),
              examples = @ExampleObject(value = "{\"username\": \"john\", \"password\": \"password\", \"overwrite\": true}")
          )
      )
      @RequestBody CreateTestUserRequest body
  ) {
    boolean overwrite = body.overwrite != null && body.overwrite;
    return authService.createTestUser(body.username, body.password, overwrite);
  }

}
