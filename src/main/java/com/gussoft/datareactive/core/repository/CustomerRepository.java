package com.gussoft.datareactive.core.repository;

import com.gussoft.datareactive.core.models.Customer;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends R2dbcRepository<Customer, Long> {

  @Query("SELECT s FROM Customer s WHERE s.name LIKE %$1%")
  Flux<Customer> findByNames(String name);

  @Transactional
  @Modifying
  @Query("update Customer set name=$1, age=$2, address_id=$3, status=$4 where id=$5")
  Mono<Customer> updateCustomer(String name, Integer age, Integer addressId, String status, Long id);
  //@Query("UPDATE Customer SET name = :name, age = :age, address_id = :addressId, status = :status WHERE id = :id")
}
