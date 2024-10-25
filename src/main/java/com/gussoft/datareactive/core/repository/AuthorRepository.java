package com.gussoft.datareactive.core.repository;

import com.gussoft.datareactive.core.models.Author;
import java.time.LocalDate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AuthorRepository extends R2dbcRepository<Author, Long> {

  @Query("UPDATE authors SET first_name=$1, last_name=$2, birthdate=$3 WHERE author_id=$4")
  Mono<Author> updateData(String firstName, String lastName, LocalDate birthdate, Long authorId);

  @Query("SELECT a.author_id, a.first_name, a.last_name, a.first_name || ' ' || a.last_name as full_name, a.birthdate "
    + "FROM authors a WHERE a.first_name like CONCAT('%',:q ,'%') or a.last_name like CONCAT('%',:q ,'%') "
    + "ORDER BY a.author_id asc "
    + "LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}")
  Flux<Author> findByQ(@Param("q") String q, @Param("pageable") Pageable pageable);

  @Query("SELECT count(a.author_id) "
    + "FROM authors a WHERE a.first_name like '%' || :q || '%' or a.last_name like '%' || :q || '%'")
  Mono<Integer> findCountByQ(@Param("q") String q);
}
