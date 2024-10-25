package com.gussoft.datareactive.core.repository;

import com.gussoft.datareactive.core.models.Address;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AddressRepository extends R2dbcRepository<Address, Long> {

  @Query("SELECT s FROM Address s WHERE s.city LIKE %$1%")
  Flux<Address> findByNames(String name);

}
