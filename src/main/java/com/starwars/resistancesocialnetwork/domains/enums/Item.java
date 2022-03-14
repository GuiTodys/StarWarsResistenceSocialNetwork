package com.starwars.resistancesocialnetwork.domains.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Item {
    WEAPON(4),
    AMMO(3),
    WATER(2),
    FOOD(1);

    final Integer value;

}
