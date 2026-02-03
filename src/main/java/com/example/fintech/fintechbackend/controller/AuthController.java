package com.example.fintech.fintechbackend.controller;

import com.example.fintech.fintechbackend.model.User;
import com.example.fintech.fintechbackend.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public User register(@RequestBody Map<String, String> body) {
    String username = body.get("username");
    String password = body.get("password");

    return authService.register(username, password);
  }

  @PostMapping("/login")
  public Map<String, String> login(@RequestBody Map<String, String> body) {
    String username = body.get("username");
    String password = body.get("password");

    return authService.login(username, password);
  }
}