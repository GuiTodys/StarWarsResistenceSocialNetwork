package com.starwars.resistancesocialnetwork.builders.domain;

import com.starwars.resistancesocialnetwork.domains.Trade;
import com.starwars.resistancesocialnetwork.domains.enums.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TradeDomainBuilder {
    @Builder.Default
    List<Item> buyer = List.of(Item.WEAPON,Item.WATER);
    @Builder.Default
    List<Item> seller = List.of(Item.FOOD,Item.FOOD,Item.FOOD,Item.FOOD,Item.FOOD,Item.FOOD);

    public Trade toDomain(){
        return Trade.builder()
                .buyer(this.buyer)
                .seller(this.seller)
                .build();
    }
}
