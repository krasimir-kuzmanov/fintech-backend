package com.example.fintech.fintechbackend.exception;

import com.example.fintech.fintechbackend.model.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(FintechException.class)
  public ResponseEntity<Map<String, String>> handleFintechException(FintechException exception) {
    if (exception.getErrorCode() == ErrorCode.UNAUTHORIZED
        || exception.getErrorCode() == ErrorCode.INVALID_CREDENTIALS) {
      return ResponseEntity
          .status(HttpStatus.UNAUTHORIZED)
          .body(Map.of("error", exception.getErrorCode().name()));
    }

    if (exception.getErrorCode() == ErrorCode.FORBIDDEN) {
      return ResponseEntity
          .status(HttpStatus.FORBIDDEN)
          .body(Map.of("error", exception.getErrorCode().name()));
    }

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(Map.of("error", exception.getErrorCode().name()));
  }
}
