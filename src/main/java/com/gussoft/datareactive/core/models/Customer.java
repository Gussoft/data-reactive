package com.gussoft.datareactive.core.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "customer", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Customer implements Serializable {

  @Id
  private Long id;
  private String name;
  private int age;
  @Transient
  private Address address;
  private Integer addressId;
  private String status;
}
