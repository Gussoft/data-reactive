package com.gussoft.datareactive.core.business.impl;

import static com.gussoft.datareactive.core.utils.Constrains.NOT_FOUND;

import com.gussoft.datareactive.core.business.AuthorService;
import com.gussoft.datareactive.core.exception.ApiException;
import com.gussoft.datareactive.core.mapper.AuthorMapper;
import com.gussoft.datareactive.core.models.Author;
import com.gussoft.datareactive.core.repository.AuthorRepository;
import com.gussoft.datareactive.core.repository.BookRepository;
import com.gussoft.datareactive.integration.transfer.record.AuthorRecord;
import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Log4j2
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository repository;
  private final BookRepository bookRepository;

  private AuthorMapper mapper;

  @Override
  public Flux<AuthorRecord> findAll() {
    return repository.findAll()
      .map(mapper::authorRecordToAuthor)
      .sort(Comparator.comparing(AuthorRecord::authorId));
  }

  @Override
  public Mono<AuthorRecord> findById(Long id) {
    return repository.findById(id)
      .switchIfEmpty(Mono.error(new ApiException(NOT_FOUND, HttpStatus.NOT_FOUND)))
      .map(mapper::authorRecordToAuthor);
  }

  @Override
  public Mono<Page<AuthorRecord>> findAllPage(String name, Pageable pageable) {
    return repository.findByQ(name, pageable)
      .collectList()
      .switchIfEmpty(Mono.error(new ApiException(NOT_FOUND, HttpStatus.NOT_FOUND)))
      .zipWith(repository.findCountByQ(name))
      .map(result -> {
        PageImpl<Author> authors = new PageImpl<>(result.getT1(), pageable, result.getT2());
        return authors.map(mapper::authorRecordToAuthor);
      });

  }

  @Override
  @Transactional
  public Mono<AuthorRecord> save(AuthorRecord request) {
    return repository.save(mapper.toAuthorRecord(request))
      .map(mapper::authorRecordToAuthor);
  }

  @Override
  public Mono<AuthorRecord> update(AuthorRecord request, Long id) {
    return repository.findById(id)
      .switchIfEmpty(Mono.error(new ApiException(NOT_FOUND, HttpStatus.NOT_FOUND)))
      .flatMap(author -> repository.updateData(request.firstName(), request.lastName(),
          request.birthdate(), author.getAuthorId())
        .flatMap(saved -> repository.findById(saved.getAuthorId())
          .map(mapper::authorRecordToAuthor)));
  }

  @Override
  public Mono<Void> deleteById(Long id) {
    return null;
  }
}

//        repository.updateData(request.firstName(), request.lastName(),
//            request.birthdate(), author.getAuthorId())
//Author data = mapper.toAuthorRecord(request);
//        data.setAuthorId(author.getAuthorId());
//        return repository.save(data).map(mapper::authorRecordToAuthor);