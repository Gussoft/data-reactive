package com.gussoft.datareactive.core.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "books", schema = "public")
@Getter
@Setter
public class Book implements Serializable {

  @Id
  @Column("book_id")
  private Long bookId;
  @Column("title")
  private String title;
  @JsonFormat(pattern = "dd/MM/yyyy")
  @Column("publication_date")
  private LocalDate publicationDate;
  @Column("online_availability")
  private Boolean onlineAvailability;

}
