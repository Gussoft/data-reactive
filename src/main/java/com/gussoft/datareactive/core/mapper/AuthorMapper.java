package com.gussoft.datareactive.core.mapper;

import com.gussoft.datareactive.core.models.Author;
import com.gussoft.datareactive.integration.transfer.record.AuthorRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {

  AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

  Author toAuthorRecord(AuthorRecord request);

  AuthorRecord authorRecordToAuthor(Author request);

}
