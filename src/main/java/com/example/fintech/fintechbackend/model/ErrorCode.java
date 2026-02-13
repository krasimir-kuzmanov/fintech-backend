package com.example.fintech.fintechbackend.model;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

  FORBIDDEN(HttpStatus.FORBIDDEN),
  INSUFFICIENT_FUNDS(HttpStatus.BAD_REQUEST),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
  INVALID_AMOUNT(HttpStatus.BAD_REQUEST),
  INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED),
  INVALID_REQUEST(HttpStatus.BAD_REQUEST),
  SAME_ACCOUNT_TRANSFER(HttpStatus.BAD_REQUEST),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
  USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST);

  private final HttpStatus httpStatus;

  ErrorCode(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

}
