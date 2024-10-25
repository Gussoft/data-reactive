package com.gussoft.datareactive.core.business.impl;

import static com.gussoft.datareactive.core.utils.Constrains.NOT_FOUND;

import com.gussoft.datareactive.core.business.PlayerService;
import com.gussoft.datareactive.core.exception.ApiException;
import com.gussoft.datareactive.core.exception.NotFoundException;
import com.gussoft.datareactive.core.mapper.PlayerMapper;
import com.gussoft.datareactive.core.models.Player;
import com.gussoft.datareactive.core.repository.PlayerRepository;
import com.gussoft.datareactive.integration.transfer.record.PlayerRecord;
import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class PlayerServiceImpl implements PlayerService {

  private final PlayerRepository repository;

  private PlayerMapper mapper;

  @Override
  public Flux<PlayerRecord> getAll() {
    return repository.findAll()
      .map(mapper::playerRecordToPlayer)
      .sort(Comparator.comparing(PlayerRecord::id));
  }

  @Override
  public Mono<Page<PlayerRecord>> getAllToPage(Pageable pageable, String name) {
    return repository.findByNames(name, pageable.getPageSize(), pageable.getOffset())
      .collectList()
      .switchIfEmpty(Mono.error(new ApiException(NOT_FOUND, HttpStatus.NO_CONTENT)))
      .zipWith(repository.findCountByQ(name))
      .map(result -> {
        PageImpl<Player> players = new PageImpl<>(result.getT1(), pageable, result.getT2());
        return players.map(play -> mapper.playerRecordToPlayer(play));
      });
  }

  @Override
  public Mono<PlayerRecord> getById(Long id) {
    return repository.findById(id)
      .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
      .map(mapper::playerRecordToPlayer);
  }

  @Override
  public Mono<PlayerRecord> create(PlayerRecord request) {
    return repository.save(mapper.toPlayerRecord(request))
      .map(mapper::playerRecordToPlayer);
  }

  @Override
  public Mono<PlayerRecord> update(PlayerRecord request, Long id) {
    return repository.findById(id)
      .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
      .flatMap(player ->
        repository.updatePlayer(
            request.name(), request.nick(), player.getId())
          .map(mapper::playerRecordToPlayer)
      );
  }

  @Override
  public Mono<Void> delete(Long id) {
    return repository.findById(id)
      .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
      .flatMap(player -> repository.deleteById(player.getId()));
  }

}
