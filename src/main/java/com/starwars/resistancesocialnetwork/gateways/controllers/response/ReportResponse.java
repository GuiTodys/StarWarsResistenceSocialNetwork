package com.starwars.resistancesocialnetwork.gateways.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportResponse {
    private Float traitorsPercentage;
    private Float notTraitorsPercentage;
    private Integer traitorsPoints;
}
