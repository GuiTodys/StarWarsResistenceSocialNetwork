package com.starwars.resistancesocialnetwork.builders.entity;

import com.starwars.resistancesocialnetwork.gateways.controllers.response.HeadquarterResponse;
import com.starwars.resistancesocialnetwork.gateways.controllers.response.RebelResponse;
import com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.entities.HeadquarterEntity;
import com.starwars.resistancesocialnetwork.gateways.persistance.impl.database.entities.RebelEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.geo.Point;

import java.util.List;

@Data
@Builder
public class HeadQuarterEntityBuilder {
    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String name = "Pandora";
    @Builder.Default
    private Point location = new Point(10,50);
    @Builder.Default
    private List<RebelEntity> rebels = List.of(
            RebelEntityBuilder.builder().build().toEntity());

    public HeadquarterEntity toEntity(){
        return HeadquarterEntity.builder()
                .id(id)
                .name(name)
                .location(location)
                //.rebels(rebels)
                .build();
    }

}
