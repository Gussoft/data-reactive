package com.gussoft.datareactive.integration.transfer.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequest {

    private Integer dues;
    private Integer time;
    private Double interestRate;
    private Double capital;
}
