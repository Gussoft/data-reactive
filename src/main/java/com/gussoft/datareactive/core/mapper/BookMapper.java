package com.gussoft.datareactive.core.mapper;

import com.gussoft.datareactive.core.models.Book;
import com.gussoft.datareactive.integration.transfer.record.BookRecord;
import com.gussoft.datareactive.integration.transfer.request.BookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {

  BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

  Book toBookRecord(BookRecord request);

  Book toBookRequest(BookRequest request);

  BookRecord bookRecordToBook(Book request);

}
