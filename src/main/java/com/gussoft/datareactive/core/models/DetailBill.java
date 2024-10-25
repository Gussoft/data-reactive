package com.gussoft.datareactive.core.models;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "detail_bill", schema = "public")
@Getter
@Setter
public class DetailBill implements Serializable {

  @Id
  private Long idDetail;
  private Long idBill;
  private Long idProduct;
  private Integer amount;
  private BigDecimal priceBuy;

}
