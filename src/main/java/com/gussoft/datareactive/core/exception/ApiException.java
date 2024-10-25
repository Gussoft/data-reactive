package com.gussoft.datareactive.core.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

  private static final long serialVersionUID = 3840287382237745348L;

  private String message;
  private HttpStatus httpStatus;

  public ApiException(String message, HttpStatus httpStatus) {
    super(message);
    this.message = message;
    this.httpStatus = httpStatus;
  }

  @Override
  public String toString() {
    return message;
  }
}

