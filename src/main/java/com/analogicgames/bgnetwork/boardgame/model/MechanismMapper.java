package com.analogicgames.bgnetwork.boardgame.model;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MechanismMapper {
    MechanismDto mechanismToDto(Mechanism mechanism);

    Mechanism dtoToMechanism(MechanismDto mechanismDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Mechanism updateMechanismFromDto(MechanismDto mechanismDto, @MappingTarget Mechanism mechanism);
}
