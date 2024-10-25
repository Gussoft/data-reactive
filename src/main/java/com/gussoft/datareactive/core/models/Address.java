package com.gussoft.datareactive.core.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "address", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Address implements Serializable {

  @Id
  private Integer id;
  private String street;
  private String city;
  private String state;

}
