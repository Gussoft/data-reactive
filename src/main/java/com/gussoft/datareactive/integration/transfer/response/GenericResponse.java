package com.gussoft.datareactive.integration.transfer.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GenericResponse<E> {

    public GenericResponse(String message) {
        this.responseMessage = message;
    }

    public GenericResponse(String message, E data) {
        this.responseMessage = message;
        this.data = data;
    }

    public GenericResponse(String message, List<E> response) {
        this.responseMessage = message;
        this.response = response;
    }

    @JsonProperty("message")
    private String responseMessage;

    @JsonInclude(value = Include.NON_NULL)
    private E data;

    @JsonInclude(value = Include.NON_NULL)
    private List<E> response;
}
