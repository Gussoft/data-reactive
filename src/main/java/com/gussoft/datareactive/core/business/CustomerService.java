package com.gussoft.datareactive.core.business;

import com.gussoft.datareactive.integration.transfer.record.CustomerRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

  Flux<CustomerRecord> getAll();
  Mono<Page<CustomerRecord>> getAllToPage(Pageable pageable, String name);
  Flux<CustomerRecord> getByName(String name);
  Mono<CustomerRecord> getById(Long id);
  Mono<CustomerRecord> create(CustomerRecord request);
  Mono<CustomerRecord> update(CustomerRecord request, Long id);
  Mono<Void> delete(Long id);

}
