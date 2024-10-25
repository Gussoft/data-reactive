package com.gussoft.datareactive.integration.transfer.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookRequest {

  private Long bookId;
  private String title;
  @JsonFormat(pattern="dd/MM/yyyy")
  private LocalDate publicationDate;
  private Boolean onlineAvailability;
  private List<Long> authors;

}
