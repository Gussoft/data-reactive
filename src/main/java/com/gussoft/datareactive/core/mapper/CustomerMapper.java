package com.gussoft.datareactive.core.mapper;

import com.gussoft.datareactive.core.models.Customer;
import com.gussoft.datareactive.integration.transfer.record.CustomerRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

  CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);


  Customer toCustomerRecord(CustomerRecord request);

  CustomerRecord customerRecordToCustomer(Customer request);

}
