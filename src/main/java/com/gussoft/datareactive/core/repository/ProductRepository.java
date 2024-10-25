package com.gussoft.datareactive.core.repository;

import com.gussoft.datareactive.core.models.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends R2dbcRepository<Product, Long> {

  @Query("SELECT p FROM Product p WHERE p.name LIKE %$1%")
  Flux<Product> findByNames(String name);

}
