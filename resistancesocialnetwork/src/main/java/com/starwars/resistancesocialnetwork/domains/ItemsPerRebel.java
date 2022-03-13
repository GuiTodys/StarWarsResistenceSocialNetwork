package com.starwars.resistancesocialnetwork.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemsPerRebel {
    private Float weaponsPerRebel;
    private Float ammoPerRebel;
    private Float waterPerRebel;
    private Float foodPerRebel;
}
