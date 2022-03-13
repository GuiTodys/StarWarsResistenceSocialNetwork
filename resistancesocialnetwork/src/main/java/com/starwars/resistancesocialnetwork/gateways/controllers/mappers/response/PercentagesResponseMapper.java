package com.starwars.resistancesocialnetwork.gateways.controllers.mappers.response;

import com.starwars.resistancesocialnetwork.domains.Percentages;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.gateways.controllers.response.PercentagesResponse;
import com.starwars.resistancesocialnetwork.gateways.controllers.response.RebelResponse;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PercentagesResponseMapper {
    PercentagesResponseMapper INSTANCE = Mappers.getMapper(PercentagesResponseMapper.class);

    Percentages toDomain(PercentagesResponse percentagesResponse);

    @InheritInverseConfiguration
    PercentagesResponse toResponse(Percentages percentages);
}
