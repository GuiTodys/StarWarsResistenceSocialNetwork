package com.starwars.resistancesocialnetwork.gateways.controllers.request;

import com.starwars.resistancesocialnetwork.domains.Rebel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeadquarterRequest {
    private Long id;
    @NotEmpty
    @Size(min = 2, max = 50)
    private String name;
    @NotNull
    private Point location;
    private List<Rebel> rebels;
}
