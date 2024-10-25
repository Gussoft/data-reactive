package com.gussoft.datareactive.core.mapper;

import com.gussoft.datareactive.core.models.Address;
import com.gussoft.datareactive.integration.transfer.record.AddressRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {

  AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

  Address toAddressRecord(AddressRecord request);

  AddressRecord addressRecordToAddress(Address request);

}
