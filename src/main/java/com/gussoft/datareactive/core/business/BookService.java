package com.gussoft.datareactive.core.business;

import com.gussoft.datareactive.integration.transfer.record.BookRecord;
import com.gussoft.datareactive.integration.transfer.request.BookRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface BookService {

  Mono<BookRecord> findById(Long id);
  Mono<Page<BookRecord>> findAllPage(String name, Pageable pageable);
  Mono<BookRecord> save(BookRequest request);
  Mono<Void> deleteById(Long id);
}
