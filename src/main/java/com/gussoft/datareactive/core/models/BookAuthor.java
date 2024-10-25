package com.gussoft.datareactive.core.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "book_authors", schema = "public")
@Getter
@Setter
@AllArgsConstructor
public class BookAuthor implements Serializable {

  @Id
  private Long id;
  private Long bookId;
  private Long authorId;

}
