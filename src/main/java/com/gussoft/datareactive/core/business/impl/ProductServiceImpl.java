package com.gussoft.datareactive.core.business.impl;

import static com.gussoft.datareactive.core.utils.Constrains.NOT_FOUND;

import com.gussoft.datareactive.core.business.ProductService;
import com.gussoft.datareactive.core.exception.NotFoundException;
import com.gussoft.datareactive.core.mapper.CategoryMapper;
import com.gussoft.datareactive.core.mapper.ProductMapper;
import com.gussoft.datareactive.core.models.Product;
import com.gussoft.datareactive.core.repository.CategoryRepository;
import com.gussoft.datareactive.core.repository.ProductRepository;
import com.gussoft.datareactive.integration.transfer.record.ProductRecord;
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
public class ProductServiceImpl implements ProductService {

  private final ProductRepository repository;
  private final CategoryRepository categoryRepo;
  private ProductMapper mapper;
  private CategoryMapper catMapper;

  @Override
  public Flux<ProductRecord> getAll() {
    return repository.findAll()
      .map(mapper::recordToEntity)
      .sort(Comparator.comparing(ProductRecord::id));
  }

  @Override
  public Mono<Page<ProductRecord>> getAllToPage(Pageable pageable, String name) {
    return null;
  }

  @Override
  public Flux<ProductRecord> getByName(String name) {
    return repository.findByNames(name)
      .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
      .map(mapper::recordToEntity);
  }

  @Override
  public Mono<ProductRecord> getById(Long id) {
    return repository.findById(id)
      .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
      .flatMap(entity -> {
          if (entity.getId() == null) {
            return Mono.just(mapper.recordToEntity(entity));
          }
          return categoryRepo.findById(entity.getId())
            .map(address -> {
              entity.setIdCategory(address.getIdCategory());
              return mapper.recordToEntity(entity);
            });
        });
  }

  @Override
  public Mono<ProductRecord> create(ProductRecord request) {
    if (request.category() == null) {
      return repository.save(mapper.toRecord(request))
        .map(mapper::recordToEntity);
    }
    return categoryRepo.save(catMapper.toRecord(request.category()))
      .flatMap(category -> {
        Product data = mapper.toRecord(request);
        data.setIdCategory(category.getIdCategory());
        return repository.save(data)
          .map(mapper::recordToEntity);
      });
  }

  @Override
  public Mono<ProductRecord> update(ProductRecord request, Long id) {
    return repository.findById(id)
      .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
      .flatMap(entity -> {
        if (request.category() == null) {
          return repository.save(mapper.toRecord(request))
            .map(mapper::recordToEntity);
        }
        return categoryRepo.findById(request.category().id())
          .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
          .flatMap(category -> {
            category = catMapper.toRecord(request.category());
            return categoryRepo.save(category)
              .flatMap(saved -> {
                Product data = mapper.toRecord(request);
                data.setIdCategory(saved.getIdCategory());
                return repository.save(data)
                  .map(c -> mapper.recordToEntity(c))
                  .doOnError(err -> log.error("Error " + err.getMessage()));
              });
          });
      });
  }

  @Override
  public Mono<Void> delete(Long id) {
    return repository.findById(id)
      .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
      .flatMap(customer -> {
        if (customer.getIdCategory() == null) {
          return repository.deleteById(customer.getId());
        }
        return repository.deleteById(customer.getId())
          .then(categoryRepo.deleteById(customer.getIdCategory()));
        });
  }
}
