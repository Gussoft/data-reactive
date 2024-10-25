package com.gussoft.datareactive.integration.expose;

import com.gussoft.datareactive.core.business.AuthorService;
import com.gussoft.datareactive.core.business.BookService;
import com.gussoft.datareactive.core.exception.ApiException;
import com.gussoft.datareactive.integration.transfer.record.AuthorRecord;
import com.gussoft.datareactive.integration.transfer.record.BookRecord;
import com.gussoft.datareactive.integration.transfer.request.BookRequest;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class BookController {

  private final BookService service;

  @GetMapping("/books/{id}")
  public Mono<ResponseEntity<BookRecord>> getItemById(@PathVariable Long id) {
    return service.findById(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping("/books")
  public Mono<ResponseEntity<BookRecord>> create(@RequestBody BookRequest request) {
    return service.save(request)
      .map(book -> ResponseEntity
        .created(URI.create("/api/v1/books".concat("/" + book.bookId()))).body(book));
  }
}
