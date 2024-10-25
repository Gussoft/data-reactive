package com.gussoft.datareactive.core.repository;

import com.gussoft.datareactive.core.models.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepository extends R2dbcRepository<Book, Long> {

  @Query("SELECT count(b.book_id) FROM books b WHERE b.title like '%' || :q || '%' ")
  Mono<Integer> findCountByQ(@Param("q") String q);

  @Query("SELECT ba.book_id as bookId, b.title as title, "
			+ "b.publication_date as publicationDate, "
			+ "b.online_availability as onlineAvailability, "
			+ "GROUP_CONCAT(a.first_name||' '||a.last_name SEPARATOR ', ') as concatAuthors "
            + "FROM book_authors ba "
            + "INNER JOIN books b ON ba.book_id = b.book_id "
            + "INNER JOIN authors a ON ba.author_id = a.author_id "
            + "WHERE b.title like '%' || :q || '%' GROUP BY b.book_id "
            + "ORDER BY b.book_id asc "
            + "LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}")
  Flux<Book> findByQ(@Param("q") String q, @Param("pageable") Pageable pageable);

}
