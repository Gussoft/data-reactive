package com.gussoft.datareactive.core.mapper;

import com.gussoft.datareactive.core.models.Category;
import com.gussoft.datareactive.integration.transfer.record.CategoryRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

  CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

  @Mapping(source = "id", target = "idCategory")
  @Mapping(source = "name", target = "name")
  Category toRecord(CategoryRecord request);

  @Mapping(source = "idCategory", target = "id")
  @Mapping(source = "name", target = "name")
  CategoryRecord recordToEntity(Category request);

}
