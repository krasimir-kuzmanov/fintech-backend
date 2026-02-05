package com.example.fintech.fintechbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(FintechException.class)
  public ResponseEntity<Map<String, String>> handleFintechException(FintechException exception) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(Map.of("error", exception.getErrorCode().name()));
  }
}
