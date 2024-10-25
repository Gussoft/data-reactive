package com.gussoft.datareactive.core.business;

import com.gussoft.datareactive.integration.transfer.record.PlayerRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerService {

  Flux<PlayerRecord> getAll();
  Mono<Page<PlayerRecord>> getAllToPage(Pageable pageable, String name);

  Mono<PlayerRecord> getById(Long id);

  Mono<PlayerRecord> create(PlayerRecord request);

  Mono<PlayerRecord> update(PlayerRecord request, Long id);

  Mono<Void> delete(Long id);

}
