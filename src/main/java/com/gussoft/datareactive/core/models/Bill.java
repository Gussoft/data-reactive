package com.gussoft.datareactive.core.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Flux;

@Table(name = "bill", schema = "public")
@Getter
@Setter
public class Bill implements Serializable {
  @Id
  private Long id;
  private Long idCustomer;
  private String numberBill;
  private LocalDateTime createAt;
  private Flux<DetailBill> details;
  private BigDecimal total;
  
}
