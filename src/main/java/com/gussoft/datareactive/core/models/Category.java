package com.gussoft.datareactive.core.models;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "category", schema = "public")
@Getter
@Setter
public class Category implements Serializable {
  @Id
  private Long idCategory;
  private String name;
  
}
