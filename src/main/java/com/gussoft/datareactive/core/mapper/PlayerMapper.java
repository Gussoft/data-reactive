package com.gussoft.datareactive.core.mapper;

import com.gussoft.datareactive.core.models.Player;
import com.gussoft.datareactive.integration.transfer.record.PlayerDto;
import com.gussoft.datareactive.integration.transfer.record.PlayerRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerMapper {

  PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

  @Mappings({
    @Mapping(source = "id", target = "id"),
    @Mapping(source = "name", target = "name"),
    @Mapping(source = "nick", target = "nick")
  })
  Player toPlayerDto(PlayerDto request);

  Player toPlayerRecord(PlayerRecord request);

  PlayerRecord playerRecordToPlayer(Player request);

}
