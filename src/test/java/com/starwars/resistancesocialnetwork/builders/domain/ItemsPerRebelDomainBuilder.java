package com.starwars.resistancesocialnetwork.builders.domain;

import com.starwars.resistancesocialnetwork.domains.ItemsPerRebel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemsPerRebelDomainBuilder {
    @Builder.Default
    private Float weaponsPerRebel = 2.5f;
    @Builder.Default
    private Float ammoPerRebel = 500.5f;
    @Builder.Default
    private Float waterPerRebel = 5.6f;
    @Builder.Default
    private Float foodPerRebel = 15.8f;

    public ItemsPerRebel toDomain(){
        return ItemsPerRebel.builder()
                .weaponsPerRebel(weaponsPerRebel)
                .ammoPerRebel(ammoPerRebel)
                .waterPerRebel(waterPerRebel)
                .foodPerRebel(foodPerRebel)
                .build();
    }
}
