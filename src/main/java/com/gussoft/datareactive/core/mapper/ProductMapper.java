package com.gussoft.datareactive.core.mapper;

import com.gussoft.datareactive.core.models.Product;
import com.gussoft.datareactive.integration.transfer.record.ProductRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

  @Mapping(source = "category.id", target = "idCategory")
  Product toRecord(ProductRecord request);

  @Mapping(source = "idCategory", target = "category.id")
  ProductRecord recordToEntity(Product request);

}
