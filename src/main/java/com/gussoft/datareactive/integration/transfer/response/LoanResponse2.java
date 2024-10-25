package com.gussoft.datareactive.integration.transfer.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse2 {
/*
    private Integer cuotas = 0;
    private LocalDate dateEnd;
    private Double interes = 0.0;
    private Double capital = 0.0;
    private Double total = 0.0;
*/
    private int installmentNumber;
    private LocalDate paymentDate;
    private double remainingCapital;
    private double interestPayment;
    private double principalPayment;
    private double monthlyPayment;
}
