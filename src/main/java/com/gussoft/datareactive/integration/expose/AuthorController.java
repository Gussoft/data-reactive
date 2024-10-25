package com.gussoft.datareactive.integration.expose;

import com.gussoft.datareactive.core.business.AuthorService;
import com.gussoft.datareactive.core.exception.ApiException;
import com.gussoft.datareactive.integration.transfer.record.AuthorRecord;
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
public class AuthorController {

  private final AuthorService service;

  @GetMapping("/authors")
  public Flux<AuthorRecord> listAll() {
    return service.findAll();
  }

  @GetMapping("/authors/{id}")
  public Mono<ResponseEntity<AuthorRecord>> getItemById(@PathVariable Long id) {
    return service.findById(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping("/authors")
  public Mono<ResponseEntity<AuthorRecord>> create(@RequestBody AuthorRecord request) {
    return service.save(request)
      .map(author -> ResponseEntity
        .created(URI.create("/api/v1/address".concat("/" + author.authorId()))).body(author));
  }

  @PutMapping("/authors/{id}")
  public Mono<ResponseEntity<AuthorRecord>> update(
    @RequestBody AuthorRecord request, @PathVariable Long id) throws ApiException {
    return service.update(request, id)
      .map(ResponseEntity::ok);
  }

}
