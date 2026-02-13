package com.example.fintech.fintechbackend.exception;

import com.example.fintech.fintechbackend.model.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(FintechException.class)
  public ResponseEntity<Map<String, String>> handleFintechException(FintechException exception) {
    ErrorCode errorCode = exception.getErrorCode();
    return ResponseEntity
        .status(errorCode.getHttpStatus())
        .body(Map.of("error", errorCode.name()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleGenericException(Exception exception) {
    ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    return ResponseEntity
        .internalServerError()
        .body(Map.of("error", errorCode.name()));
  }
}
