package com.starwars.resistancesocialnetwork.builders.request;

import com.starwars.resistancesocialnetwork.gateways.controllers.request.HeadquarterRequest;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.RebelRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.geo.Point;

import java.util.List;

@Data
@Builder
public class HeadQuarterRequestBuilder {
    @Builder.Default
    private String name = "Pandora";
    @Builder.Default
    private Point location = new Point(10,50);
    @Builder.Default
    private List<RebelRequest> rebels = List.of(
            RebelRequestBuilder.builder().build().toRequest());

    public HeadquarterRequest toRequest(){
        return HeadquarterRequest.builder()
                .name(name)
                .location(location)
                .rebels(rebels)
                .build();
    }

}
