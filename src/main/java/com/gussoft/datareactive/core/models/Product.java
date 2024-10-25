package com.gussoft.datareactive.core.models;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "product", schema = "public")
@Getter
@Setter
public class Product implements Serializable {
  @Id
  private Long id;
  private String name;
  private String description;
  private BigDecimal priceSell;
  private Integer stock;
  private Long idCategory;

}
