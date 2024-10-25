package com.gussoft.datareactive.core.business.impl;

import com.gussoft.datareactive.core.business.LoanService;
import com.gussoft.datareactive.integration.transfer.request.LoanRequest;
import com.gussoft.datareactive.integration.transfer.request.PaymentScheduleRequest;
import com.gussoft.datareactive.integration.transfer.response.LoanResponse;
import com.gussoft.datareactive.integration.transfer.response.LoanResponse2;
import com.gussoft.datareactive.integration.transfer.response.PaymentResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {


    @Override
    public Mono<List<LoanResponse>> calculateRate(LoanRequest request) {
        Double interestM = (request.getInterestRate() / 12) / 100;
        Double result = (1 - Math.pow(1 + interestM, request.getTime() * -1)) / interestM;
        Double share = request.getCapital() / result;
        log.info("Cuota fija mensual es : {}", share);
        log.info("Tasa de Interez mensual es : {}", interestM);
        List<LoanResponse> nCuotas = new ArrayList<>();
        Double saldoFinal = request.getCapital();

        Integer cuotas = 0;
        Double pagare = 0.0;
        Double interes = 0.0;
        Double capital = 0.0;
        Double saldoInicial = 0.0;
        Double totalInteres = 0.0;
        Double abonoCapital = 0.0;
        Double acumulado = 0.0;

        for (int i = 1; i < request.getTime() + 1; i++) {
            LoanResponse response = new LoanResponse();
            interes = (double) Math.round(saldoFinal * interestM);
            totalInteres = response.getTotalInteres() + response.getInteres();
            saldoInicial = saldoFinal;
            capital = request.getDues() - response.getInteres();
            abonoCapital += capital;
            saldoFinal -= capital;
            cuotas += request.getDues();
            response.setNCuotas(cuotas);
            response.setAbonoCapital(abonoCapital);
            response.setInteres(interes);
            response.setSaldoInicial(saldoInicial);
            response.setSaldoFinal(saldoFinal);
            response.setTotalInteres(totalInteres);
            response.setCapital(capital);
            nCuotas.add(response);
        }
        return Mono.just(nCuotas);
    }

    @Override
    public Flux<PaymentResponse> simulationLoan(PaymentScheduleRequest request) {

        BigDecimal monthlyInterestRate = request.getInterestRate().divide(BigDecimal.valueOf(12), 5, BigDecimal.ROUND_HALF_UP);

        // Calcular el monto total de la cuota
        BigDecimal totalPaymentAmount = calculateTotalPaymentAmount(request.getLoanAmount(), monthlyInterestRate, request.getNumberOfPayments());

        // Calcular los pagos individuales
        List<PaymentResponse> payments = new ArrayList<>();
        BigDecimal remainingLoanAmount = request.getLoanAmount();
        for (int i = 1; i <= request.getNumberOfPayments(); i++) {
            BigDecimal interestPayment = remainingLoanAmount.multiply(monthlyInterestRate);
            BigDecimal principalPayment = totalPaymentAmount.subtract(interestPayment);
            remainingLoanAmount = remainingLoanAmount.subtract(principalPayment);

            PaymentResponse payment = new PaymentResponse(
                    i,
                    LocalDate.now().plusMonths(i),
                    principalPayment,
                    interestPayment,
                    totalPaymentAmount
            );
            payments.add(payment);
        }

        return Flux.fromIterable(payments);
    }

    private BigDecimal calculateTotalPaymentAmount(BigDecimal loanAmount, BigDecimal monthlyInterestRate, int numberOfPayments) {
        BigDecimal factor = monthlyInterestRate.multiply(BigDecimal.ONE.add(monthlyInterestRate).pow(numberOfPayments));
        BigDecimal totalPaymentAmount = loanAmount.multiply(monthlyInterestRate).multiply(factor)
                .divide(factor.subtract(BigDecimal.ONE), 5, BigDecimal.ROUND_HALF_UP);
        return totalPaymentAmount;
    }

}
