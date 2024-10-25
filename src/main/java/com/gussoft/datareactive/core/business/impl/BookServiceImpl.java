package com.gussoft.datareactive.core.business.impl;

import static com.gussoft.datareactive.core.utils.Constrains.NOT_FOUND;

import com.gussoft.datareactive.core.business.BookService;
import com.gussoft.datareactive.core.exception.ApiException;
import com.gussoft.datareactive.core.mapper.AuthorMapper;
import com.gussoft.datareactive.core.mapper.BookMapper;
import com.gussoft.datareactive.core.models.Author;
import com.gussoft.datareactive.core.models.Book;
import com.gussoft.datareactive.core.models.BookAuthor;
import com.gussoft.datareactive.core.repository.BookAuthorRepository;
import com.gussoft.datareactive.core.repository.BookRepository;
import com.gussoft.datareactive.core.utils.DateUtil;
import com.gussoft.datareactive.integration.transfer.record.BookRecord;
import com.gussoft.datareactive.integration.transfer.request.BookRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Log4j2
public class BookServiceImpl implements BookService {

  private final BookRepository repository;
  private final BookAuthorRepository baRepository;

  private BookMapper mapper;

  @Override
  public Mono<BookRecord> findById(Long id) {
    return repository.findById(id)
      .switchIfEmpty(Mono.error(new ApiException(NOT_FOUND, HttpStatus.NOT_FOUND)))
      .map(mapper::bookRecordToBook);
  }

  @Override
  public Mono<Page<BookRecord>> findAllPage(String name, Pageable pageable) {
    return repository.findByQ(name, pageable)
      .collectList()
      .switchIfEmpty(Mono.error(new ApiException(NOT_FOUND, HttpStatus.NOT_FOUND)))
      .zipWith(repository.findCountByQ(name))
      .map(result -> {
        PageImpl<Book> books = new PageImpl<>(result.getT1(), pageable, result.getT2());
        return books.map(mapper::bookRecordToBook);
      });
  }

  @Override
  @Transactional
  public Mono<BookRecord> save(BookRequest request) {
    LocalDateTime now = DateUtil.getInstance().convertToLocalDateTime(new Date());
    log.info("Hour saved : " + now);
    return repository.save(mapper.toBookRequest(request))
      .flatMap(book -> {
        List<BookAuthor> bookAuthors = request.getAuthors()
          .stream()
          .map(authorId -> new BookAuthor(null, authorId, book.getBookId())).toList();
        return baRepository.saveAll(bookAuthors).collectList()
          .flatMap(ba -> repository.findById(book.getBookId()))
          .map(book2 -> mapper.bookRecordToBook(book2));
      });
  }

  @Override
  public Mono<Void> deleteById(Long id) {
    return null;
  }
}
