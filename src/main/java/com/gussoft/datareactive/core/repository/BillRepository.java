package com.gussoft.datareactive.core.repository;

import com.gussoft.datareactive.core.models.Bill;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends R2dbcRepository<Bill, Long> {

}
