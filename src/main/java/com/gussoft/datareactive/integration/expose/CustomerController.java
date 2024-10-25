package com.gussoft.datareactive.integration.expose;

import com.gussoft.datareactive.core.business.CustomerService;
import com.gussoft.datareactive.core.exception.ApiException;
import com.gussoft.datareactive.integration.transfer.record.CustomerRecord;
import com.gussoft.datareactive.integration.transfer.record.PlayerRecord;
import com.gussoft.datareactive.integration.transfer.response.MessageResponse;
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
public class CustomerController {

  private final CustomerService service;

  @GetMapping("/customers")
  public Flux<CustomerRecord> listAll() {
    return service.getAll();
  }

  @GetMapping("/customers/{id}")
  public Mono<ResponseEntity<CustomerRecord>> getItemById(@PathVariable Long id) {
    return service.getById(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping("/customers")
  public Mono<ResponseEntity<CustomerRecord>> create(@RequestBody CustomerRecord request) {
    return service.create(request)
      .map(play -> ResponseEntity
        .created(URI.create("/api/v1/customer".concat("/" + play.id()))).body(play));
  }

  @PutMapping("/customers/{id}")
  public Mono<ResponseEntity<CustomerRecord>> update(
    @RequestBody CustomerRecord request, @PathVariable Long id) throws ApiException {
    return service.update(request, id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.badRequest().build());
  }

  @DeleteMapping("/customers/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
    return service.delete(id).map(ResponseEntity::ok);
  }

}
