package com.gussoft.datareactive.integration.expose;

import com.gussoft.datareactive.core.business.AddressService;
import com.gussoft.datareactive.integration.transfer.record.AddressRecord;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AddressController {

  private final AddressService service;

  @GetMapping("/address")
  public Flux<AddressRecord> listAll() {
    return service.getAll();
  }

  @GetMapping("/address/{id}")
  public Mono<ResponseEntity<AddressRecord>> getItemById(@PathVariable Long id) {
    return service.getById(id)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping("/address")
  public Mono<ResponseEntity<AddressRecord>> create(@RequestBody AddressRecord request) {
    return service.create(request)
      .map(play -> ResponseEntity
        .created(URI.create("/api/v1/address".concat("/" + play.id()))).body(play));
  }
}
