package com.gussoft.datareactive.core.repository;

import com.gussoft.datareactive.core.models.Category;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends R2dbcRepository<Category, Long> {

}
