package com.gussoft.datareactive.core.business.impl;

import static com.gussoft.datareactive.core.utils.Constrains.NOT_FOUND;

import com.gussoft.datareactive.core.business.CategoryService;
import com.gussoft.datareactive.core.exception.NotFoundException;
import com.gussoft.datareactive.core.mapper.CategoryMapper;
import com.gussoft.datareactive.core.repository.CategoryRepository;
import com.gussoft.datareactive.integration.transfer.record.CategoryRecord;
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
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository repository;

  private CategoryMapper mapper;

  @Override
  public Flux<CategoryRecord> getAll() {
    return repository.findAll()
      .map(mapper::recordToEntity)
      .sort(Comparator.comparing(CategoryRecord::id));
  }

  @Override
  public Mono<CategoryRecord> findById(Long id) {
    return repository.findById(id)
      .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
      .map(mapper::recordToEntity);
  }

  @Override
  public Mono<Page<CategoryRecord>> findAllPage(String name, Pageable pageable) {
    return null;
  }

  @Override
  public Mono<CategoryRecord> save(CategoryRecord request) {
    return repository.save(mapper.toRecord(request))
      .map(mapper::recordToEntity);
  }

  @Override
  public Mono<Void> deleteById(Long id) {
    return null;
  }
}
