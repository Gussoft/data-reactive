package com.gussoft.datareactive.core.business;

import com.gussoft.datareactive.integration.transfer.request.LoanRequest;
import com.gussoft.datareactive.integration.transfer.request.PaymentScheduleRequest;
import com.gussoft.datareactive.integration.transfer.response.LoanResponse;
import com.gussoft.datareactive.integration.transfer.response.LoanResponse2;
import com.gussoft.datareactive.integration.transfer.response.PaymentResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LoanService {

    Mono<List<LoanResponse>> calculateRate(LoanRequest request);

    Flux<PaymentResponse> simulationLoan(PaymentScheduleRequest request);
}
