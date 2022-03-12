package com.starwars.resistancesocialnetwork.gateways.controllers.mappers.request;

import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.domains.Trade;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.RebelRequest;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.TradeRequest;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TradeRequestMapper {
    TradeRequestMapper INSTANCE = Mappers.getMapper(TradeRequestMapper.class);

    Trade toDomain(TradeRequest tradeRequest);

    @InheritInverseConfiguration
    TradeRequest toRequest(Trade trade);
}
