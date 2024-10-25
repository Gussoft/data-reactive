package com.gussoft.datareactive.core.business;

import com.gussoft.datareactive.integration.transfer.record.ProductRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

  Flux<ProductRecord> getAll();
  Mono<Page<ProductRecord>> getAllToPage(Pageable pageable, String name);
  Flux<ProductRecord> getByName(String name);
  Mono<ProductRecord> getById(Long id);
  Mono<ProductRecord> create(ProductRecord request);
  Mono<ProductRecord> update(ProductRecord request, Long id);
  Mono<Void> delete(Long id);

}
