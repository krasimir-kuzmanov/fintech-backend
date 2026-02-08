package com.example.fintech.fintechbackend.service;

import com.example.fintech.fintechbackend.constants.AuthConstants;
import com.example.fintech.fintechbackend.exception.FintechException;
import com.example.fintech.fintechbackend.model.ErrorCode;
import com.example.fintech.fintechbackend.model.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

@Service
public class AuthService {

  private final Map<String, User> users = new ConcurrentHashMap<>();
  private final Map<String, String> tokensByValue = new ConcurrentHashMap<>();
  private final Map<String, Instant> revokedTokensByValue = new ConcurrentHashMap<>();

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
    tokensByValue.put(token, user.getId());
    revokedTokensByValue.remove(token);

    return Map.of("userId", user.getId(), "token", token);
  }

  public String authenticate(String authorizationHeader) {
    String token = extractToken(authorizationHeader);
    if (revokedTokensByValue.containsKey(token)) {
      throw new FintechException(ErrorCode.UNAUTHORIZED);
    }

    String userId = tokensByValue.get(token);
    if (userId == null) {
      throw new FintechException(ErrorCode.UNAUTHORIZED);
    }

    return userId;
  }

  public void logout(String authorizationHeader) {
    String token = extractToken(authorizationHeader);
    boolean knownToken = tokensByValue.containsKey(token) || revokedTokensByValue.containsKey(token);
    if (!knownToken) {
      throw new FintechException(ErrorCode.UNAUTHORIZED);
    }

    tokensByValue.remove(token);
    revokedTokensByValue.putIfAbsent(token, Instant.now());
  }

  private String extractToken(String authorizationHeader) {
    if (authorizationHeader == null || authorizationHeader.isBlank()) {
      throw new FintechException(ErrorCode.UNAUTHORIZED);
    }

    if (!authorizationHeader.startsWith(AuthConstants.BEARER_PREFIX)) {
      throw new FintechException(ErrorCode.UNAUTHORIZED);
    }

    String token = authorizationHeader.substring(AuthConstants.BEARER_PREFIX.length()).trim();
    if (token.isBlank()) {
      throw new FintechException(ErrorCode.UNAUTHORIZED);
    }

    return token;
  }

  public void reset() {
    users.clear();
    tokensByValue.clear();
    revokedTokensByValue.clear();
  }
}
