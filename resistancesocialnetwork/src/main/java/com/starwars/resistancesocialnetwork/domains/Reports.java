package com.starwars.resistancesocialnetwork.domains;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reports {
    private Float traitorsPercentage;
    private Float notTraitorsPercentage;
    private Integer traitorsPoints;
}
