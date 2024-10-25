package com.gussoft.datareactive.core.business;

import com.gussoft.datareactive.integration.transfer.record.AuthorRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {

  Flux<AuthorRecord> findAll();
  Mono<AuthorRecord> findById(Long id);
  Mono<Page<AuthorRecord>> findAllPage(String name, Pageable pageable);
  Mono<AuthorRecord> save(AuthorRecord request);
  Mono<AuthorRecord> update(AuthorRecord request, Long id);
  Mono<Void> deleteById(Long id);
}
