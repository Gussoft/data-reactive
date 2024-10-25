package com.gussoft.datareactive.core.repository;

import com.gussoft.datareactive.core.models.DetailBill;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailRepository extends R2dbcRepository<DetailBill, Long> {

}
