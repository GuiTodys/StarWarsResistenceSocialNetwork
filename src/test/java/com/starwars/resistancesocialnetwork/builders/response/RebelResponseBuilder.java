package com.starwars.resistancesocialnetwork.builders.response;

import com.starwars.resistancesocialnetwork.domains.enums.Gender;
import com.starwars.resistancesocialnetwork.domains.enums.Item;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.RebelRequest;
import com.starwars.resistancesocialnetwork.gateways.controllers.response.RebelResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RebelResponseBuilder {
    @Builder.Default
    private Long id = 1L;
    @Builder.Default
    private String name = "Luke";
    @Builder.Default
    private Integer age = 18;
    @Builder.Default
    private Gender gender = Gender.MALE;
    @Builder.Default
    private Long headquarterId = 1L;
    @Builder.Default
    private List<Item> inventory = List.of(Item.WATER);
    @Builder.Default
    private Integer reports = 0;
    @Builder.Default
    private Boolean traitor = false;

    public RebelResponse toRequest(){
        return RebelResponse.builder()
                .id(id)
                .name(name)
                .age(age)
                .gender(gender)
                .headquarterId(headquarterId)
                .inventory(inventory)
                .reports(reports)
                .traitor(traitor)
                .build();
    }

}
