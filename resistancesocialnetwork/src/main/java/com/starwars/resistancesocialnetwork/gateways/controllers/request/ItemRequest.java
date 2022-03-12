package com.starwars.resistancesocialnetwork.gateways.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ItemRequest {
    WEAPON(4),
    AMMO(3),
    WATER(2),
    FOOD(1);

    final Integer value;

}
