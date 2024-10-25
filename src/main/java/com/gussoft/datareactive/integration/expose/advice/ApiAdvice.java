package com.gussoft.datareactive.integration.expose.advice;

import com.gussoft.datareactive.core.exception.ApiException;
import com.gussoft.datareactive.integration.transfer.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ApiAdvice {

  @ExceptionHandler(value = ApiException.class)
  public Mono<ResponseEntity<MessageResponse<Void>>> getException(ApiException exception) {

    MessageResponse<Void> msg = MessageResponse.<Void>builder()
      .message(exception.getMessage())
      .build();

    return Mono.just(new ResponseEntity<>(msg,exception.getHttpStatus()));
  }

}
