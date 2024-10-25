package com.gussoft.datareactive.integration.expose;

import com.gussoft.datareactive.core.business.ProductService;
import com.gussoft.datareactive.core.exception.ApiException;
import com.gussoft.datareactive.integration.transfer.record.ProductRecord;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class ProductController {

  private final ProductService service;

  @GetMapping("/products")
  public Flux<ProductRecord> listAll() {
    return service.getAll();
  }

  @GetMapping("/products/{id}")
  public Mono<ResponseEntity<ProductRecord>> getItemById(@PathVariable Long id) {
    return service.getById(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping("/products")
  public Mono<ResponseEntity<ProductRecord>> create(@RequestBody ProductRecord request) {
    return service.create(request)
      .map(prod -> ResponseEntity
        .created(URI.create("/api/v1/products".concat("/" + prod.id()))).body(prod));
  }

  @PutMapping("/products/{id}")
  public Mono<ResponseEntity<ProductRecord>> update(
    @RequestBody ProductRecord request, @PathVariable Long id) throws ApiException {
    return service.update(request, id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.badRequest().build());
  }

  @DeleteMapping("/products/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
    return service.delete(id).map(ResponseEntity::ok);
  }

}
