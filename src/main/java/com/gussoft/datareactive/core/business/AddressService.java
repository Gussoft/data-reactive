package com.gussoft.datareactive.core.business;

import com.gussoft.datareactive.integration.transfer.record.AddressRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AddressService {

  Flux<AddressRecord> getAll();
  Mono<Page<AddressRecord>> getAllToPage(Pageable pageable, String name);
  Flux<AddressRecord> getByName(String city);
  Mono<AddressRecord> getById(Long id);

  Mono<AddressRecord> create(AddressRecord request);
  Mono<AddressRecord> update(AddressRecord request, Long id);
  Mono<Void> delete(Long id);

}
