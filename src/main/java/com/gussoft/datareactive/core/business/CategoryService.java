package com.gussoft.datareactive.core.business;

import com.gussoft.datareactive.integration.transfer.record.CategoryRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

  Flux<CategoryRecord> getAll();
  Mono<CategoryRecord> findById(Long id);
  Mono<Page<CategoryRecord>> findAllPage(String name, Pageable pageable);
  Mono<CategoryRecord> save(CategoryRecord request);
  Mono<Void> deleteById(Long id);
}
