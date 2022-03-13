package com.starwars.resistancesocialnetwork.gateways.controllers.mappers.response;

import com.starwars.resistancesocialnetwork.domains.ItemsPerRebel;
import com.starwars.resistancesocialnetwork.gateways.controllers.response.ItemsPerRebelResponse;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemsPerRebelResponseMapper {
    ItemsPerRebelResponseMapper INSTANCE = Mappers.getMapper(ItemsPerRebelResponseMapper.class);

    ItemsPerRebel toDomain(ItemsPerRebelResponse itemsPerRebelResponse);

    @InheritInverseConfiguration
    ItemsPerRebelResponse toResponse(ItemsPerRebel itemsPerRebel);
}
