package com.gussoft.datareactive.integration.transfer.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageResponse<T> {
  private String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T content;

}
