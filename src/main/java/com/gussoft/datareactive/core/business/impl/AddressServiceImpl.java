package com.gussoft.datareactive.core.business.impl;

import static com.gussoft.datareactive.core.utils.Constrains.NOT_FOUND;

import com.gussoft.datareactive.core.business.AddressService;
import com.gussoft.datareactive.core.exception.NotFoundException;
import com.gussoft.datareactive.core.mapper.AddressMapper;
import com.gussoft.datareactive.core.repository.AddressRepository;
import com.gussoft.datareactive.integration.transfer.record.AddressRecord;
import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Log4j2
public class AddressServiceImpl implements AddressService {

  private final AddressRepository repository;

  private AddressMapper mapper;

    @Override
  public Flux<AddressRecord> getAll() {
    return repository.findAll()
      .map(mapper::addressRecordToAddress)
      .sort(Comparator.comparing(AddressRecord::id));
  }

  @Override
  public Mono<Page<AddressRecord>> getAllToPage(Pageable pageable, String name) {
    return null;
  }

  @Override
  public Flux<AddressRecord> getByName(String name) {
    return repository.findByNames(name)
      .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
      .map(mapper::addressRecordToAddress);
  }

  @Override
  public Mono<AddressRecord> getById(Long id) {
    return repository.findById(id)
      .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
      .map(mapper::addressRecordToAddress);
  }

  @Override
  public Mono<AddressRecord> create(AddressRecord request) {
    return repository.save(mapper.toAddressRecord(request))
      .map(mapper::addressRecordToAddress);
  }

  @Override
  public Mono<AddressRecord> update(AddressRecord request, Long id) {
    return null;
  }

  @Override
  public Mono<Void> delete(Long id) {
    return null;
  }
}
