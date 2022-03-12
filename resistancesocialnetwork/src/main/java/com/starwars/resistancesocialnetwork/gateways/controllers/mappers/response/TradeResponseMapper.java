package com.starwars.resistancesocialnetwork.gateways.controllers.mappers.response;

import com.starwars.resistancesocialnetwork.domains.Trade;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.TradeRequest;
import com.starwars.resistancesocialnetwork.gateways.controllers.response.TradeResponse;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TradeResponseMapper {
    TradeResponseMapper INSTANCE = Mappers.getMapper(TradeResponseMapper.class);

    Trade toDomain(TradeResponse tradeResponse);

    @InheritInverseConfiguration
    TradeResponse toResponse(Trade trade);
}
