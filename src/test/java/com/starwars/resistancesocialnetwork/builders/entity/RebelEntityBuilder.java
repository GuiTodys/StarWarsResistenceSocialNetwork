package com.starwars.resistancesocialnetwork.builders.entity;

import com.starwars.resistancesocialnetwork.domains.enums.Item;
import com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.entities.HeadquarterEntity;
import com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.entities.RebelEntity;
import com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.entities.enums.GenderEntity;
import com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.entities.enums.ItemEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RebelEntityBuilder {
    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String name = "Luke";
    @Builder.Default
    private Integer age = 18;
    @Builder.Default
    private GenderEntity gender = GenderEntity.MALE;
    //@Builder.Default
    //private HeadquarterEntity location = HeadQuarterEntityBuilder.builder().build().toEntity();
    @Builder.Default
    private List<ItemEntity> inventory = List.of(ItemEntity.WATER);
    @Builder.Default
    private Integer reports = 0;
    @Builder.Default
    private Boolean traitor = false;

    public RebelEntity toEntity(){
        return RebelEntity.builder()
                .id(id)
                .name(name)
                .age(age)
                .gender(gender)
                //.location(location)
                .inventory(inventory)
                .reports(reports)
                .traitor(traitor)
                .build();
    }

}
