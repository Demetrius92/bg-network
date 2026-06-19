package com.analogicgames.bgnetwork.boardgame.model;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BoardGameMapper {
    BoardGameDto boardGameToDto(BoardGame boardGame);

    BoardGame dtoToBoardGame(BoardGameDto boardGameDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BoardGame updateBoardGameFromDto(BoardGameDto boardGameDto, @MappingTarget BoardGame boardGame);
}
