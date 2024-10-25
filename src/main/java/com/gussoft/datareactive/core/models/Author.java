package com.gussoft.datareactive.core.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "authors", schema = "public")
@Getter
@Setter
@AllArgsConstructor
public class Author implements Serializable {

  @Id
  @Column("author_id")
  private Long authorId;
  @Column("first_name")
  private String firstName;
  @Column("last_name")
  private String lastName;
  @JsonFormat(pattern = "dd/MM/yyyy")
  @Column("birthdate")
  private LocalDate birthdate;

}
