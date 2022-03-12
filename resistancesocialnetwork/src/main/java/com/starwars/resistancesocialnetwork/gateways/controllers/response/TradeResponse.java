package com.starwars.resistancesocialnetwork.gateways.controllers.response;

import com.starwars.resistancesocialnetwork.gateways.controllers.request.ItemRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeResponse {

    List<ItemRequest> buyer;
    List<ItemRequest> seller;

}
