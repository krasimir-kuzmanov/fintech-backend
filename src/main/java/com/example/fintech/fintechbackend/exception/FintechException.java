package com.example.fintech.fintechbackend.exception;

import com.example.fintech.fintechbackend.model.ErrorCode;

public class FintechException extends RuntimeException {

  private final ErrorCode errorCode;

  public FintechException(ErrorCode errorCode) {
    super(errorCode.name());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}