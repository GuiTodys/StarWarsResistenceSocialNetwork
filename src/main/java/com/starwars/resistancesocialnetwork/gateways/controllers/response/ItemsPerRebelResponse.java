package com.starwars.resistancesocialnetwork.gateways.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemsPerRebelResponse {
    private Float weaponsPerRebel;
    private Float ammoPerRebel;
    private Float waterPerRebel;
    private Float foodPerRebel;
}
