package com.gussoft.datareactive.core.repository;

import com.gussoft.datareactive.core.models.BookAuthor;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAuthorRepository extends R2dbcRepository<BookAuthor, Long> {


}
