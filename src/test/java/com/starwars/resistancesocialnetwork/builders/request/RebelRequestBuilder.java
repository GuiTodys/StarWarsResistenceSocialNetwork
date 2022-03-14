package com.starwars.resistancesocialnetwork.builders.request;

import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.domains.enums.Gender;
import com.starwars.resistancesocialnetwork.domains.enums.Item;
import com.starwars.resistancesocialnetwork.gateways.controllers.request.RebelRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RebelRequestBuilder {
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

    public RebelRequest toRequest(){
        return RebelRequest.builder()
                .name(name)
                .age(age)
                .gender(gender)
                .headquarterId(headquarterId)
                .inventory(inventory)
                .build();
    }

}
