package com.starwars.resistancesocialnetwork.domains;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Percentages {
    private Float traitorsPercentage;
    private Float notTraitorsPercentage;
}
