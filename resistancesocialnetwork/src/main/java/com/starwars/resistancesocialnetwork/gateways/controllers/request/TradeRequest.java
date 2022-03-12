package com.starwars.resistancesocialnetwork.gateways.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeRequest {

    List<ItemRequest> buyer;
    List<ItemRequest> seller;

}
