package com.starwars.resistancesocialnetwork.builders.response;

import com.starwars.resistancesocialnetwork.builders.request.RebelRequestBuilder;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.HeadquarterRequest;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.RebelRequest;
import com.starwars.resistancesocialnetwork.gateways.controllers.response.HeadquarterResponse;
import com.starwars.resistancesocialnetwork.gateways.controllers.response.RebelResponse;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.geo.Point;

import java.util.List;

@Data
@Builder
public class HeadQuarterResponseBuilder {
    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String name = "Pandora";
    @Builder.Default
    private Point location = new Point(10,50);
    @Builder.Default
    private List<RebelResponse> rebels = List.of(
            RebelResponseBuilder.builder().build().toRequest());

    public HeadquarterResponse toResponse(){
        return HeadquarterResponse.builder()
                .id(id)
                .name(name)
                .location(location)
                .rebels(rebels)
                .build();
    }

}
