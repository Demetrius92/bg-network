package com.analogicgames.bgnetwork.boardgame;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BoardGameMapper {
    BoardGameDto boardGameToDto(BoardGame boardGame);

    BoardGame dtoToBoardGame(BoardGameDto boardGameDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBoardGameFromDto(BoardGameDto boardGameDto, @MappingTarget BoardGame boardGame);
}
