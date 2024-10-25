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
public class LoanResponse {

    private Integer nCuotas = 0;
    private Double pagare = 0.0;
    private Double interes = 0.0;
    private Double saldoInicial = 0.0;
    private Double saldoFinal = 0.0;
    private Double capital = 0.0;

    private Double totalInteres = 0.0;
    private Double abonoCapital = 0.0;
    private Double acumulado = 0.0;

}
