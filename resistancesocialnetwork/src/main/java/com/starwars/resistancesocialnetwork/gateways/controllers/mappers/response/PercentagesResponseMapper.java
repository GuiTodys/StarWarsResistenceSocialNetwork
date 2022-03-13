package com.starwars.resistancesocialnetwork.gateways.controllers.mappers.response;

import com.starwars.resistancesocialnetwork.domains.Reports;
import com.starwars.resistancesocialnetwork.gateways.controllers.response.ReportResponse;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PercentagesResponseMapper {
    PercentagesResponseMapper INSTANCE = Mappers.getMapper(PercentagesResponseMapper.class);

    Reports toDomain(ReportResponse percentagesResponse);

    @InheritInverseConfiguration
    ReportResponse toResponse(Reports percentages);
}
