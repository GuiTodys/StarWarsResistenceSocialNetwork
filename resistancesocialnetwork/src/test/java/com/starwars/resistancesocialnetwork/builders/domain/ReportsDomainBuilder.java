package com.starwars.resistancesocialnetwork.builders.domain;

import com.starwars.resistancesocialnetwork.domains.Reports;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportsDomainBuilder {

    @Builder.Default
    private Float traitorsPercentage = 10.00f;
    @Builder.Default
    private Float notTraitorsPercentage = 90.00f;
    @Builder.Default
    private Integer traitorsPoints = 350;

    public Reports toDomain(){
        return Reports.builder()
                .traitorsPercentage(traitorsPercentage)
                .notTraitorsPercentage(notTraitorsPercentage)
                .traitorsPoints(traitorsPoints)
                .build();
    }
}
