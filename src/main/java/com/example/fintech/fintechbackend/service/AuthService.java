package com.example.fintech.fintechbackend.service;

import com.example.fintech.fintechbackend.exception.FintechException;
import com.example.fintech.fintechbackend.model.ErrorCode;
import com.example.fintech.fintechbackend.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {
  private final Map<String, User> users = new HashMap<>();

  public User register(String username, String password) {
    if (users.containsKey(username)) {
      throw new FintechException(ErrorCode.USER_ALREADY_EXISTS);
    }

    User user = new User(UUID.randomUUID().toString(), username, password);
    users.put(username, user);

    return user;
  }

  public Map<String, String> login(String username, String password) {
    User user = users.get(username);

    if (user == null || !user.getPassword().equals(password)) {
      throw new FintechException(ErrorCode.INVALID_CREDENTIALS);
    }

    String token = UUID.randomUUID().toString(); // fake token

    return Map.of("userId", user.getId(), "token", token);
  }

  public void reset() {
    users.clear();
  }
}