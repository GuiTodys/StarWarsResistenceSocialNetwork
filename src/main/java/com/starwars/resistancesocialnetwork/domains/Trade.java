package com.starwars.resistancesocialnetwork.domains;


import com.starwars.resistancesocialnetwork.domains.enums.Item;
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
public class Trade {
    List<Item> buyer;
    List<Item> seller;
}
