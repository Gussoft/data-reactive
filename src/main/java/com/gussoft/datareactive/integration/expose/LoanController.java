package com.gussoft.datareactive.integration.expose;

import com.gussoft.datareactive.core.business.LoanService;
import com.gussoft.datareactive.integration.transfer.request.LoanRequest;
import com.gussoft.datareactive.integration.transfer.request.PaymentScheduleRequest;
import com.gussoft.datareactive.integration.transfer.response.GenericResponse;
import com.gussoft.datareactive.integration.transfer.response.LoanResponse2;
import com.gussoft.datareactive.integration.transfer.response.PaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/api")
@AllArgsConstructor
public class LoanController {

    private final LoanService service;

    @PostMapping("/loan")
    public Mono<ResponseEntity<GenericResponse>> calcularPrestamo(@RequestBody LoanRequest request) {
        return service.calculateRate(request)
                .map(data -> ResponseEntity.ok(new GenericResponse("OK", data)))
                .onErrorResume(throwable -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new GenericResponse(throwable.getMessage()))));
    }

    @PostMapping("/loans")
    @ResponseStatus(HttpStatus.OK)
    public Flux<PaymentResponse> calcularPrestamo2(@RequestBody PaymentScheduleRequest request) {
        return service.simulationLoan(request);
    }

}
