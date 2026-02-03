package com.example.fintech.fintechbackend.controller;

import com.example.fintech.fintechbackend.model.User;
import com.example.fintech.fintechbackend.service.AuthService;
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

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Authentication and registration")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  @Operation(summary = "Register", description = "Register a new user account.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Registration successful"),
      @ApiResponse(responseCode = "400", description = "Invalid request")
  })
  public User register(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Registration payload",
          required = true,
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = Map.class),
              examples = @ExampleObject(value = "{\"username\": \"alice\", \"password\": \"secret\"}")
          )
      )
      @RequestBody Map<String, String> body
  ) {
    String username = body.get("username");
    String password = body.get("password");

    return authService.register(username, password);
  }

  @PostMapping("/login")
  @Operation(summary = "Login", description = "Authenticate a user and return a token.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Login successful"),
      @ApiResponse(responseCode = "401", description = "Invalid credentials")
  })
  public Map<String, String> login(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Login payload",
          required = true,
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = Map.class),
              examples = @ExampleObject(value = "{\"username\": \"alice\", \"password\": \"secret\"}")
          )
      )
      @RequestBody Map<String, String> body
  ) {
    String username = body.get("username");
    String password = body.get("password");

    return authService.login(username, password);
  }
}
