package com.starwars.resistancesocialnetwork.domains;

import com.starwars.resistancesocialnetwork.domains.enums.Gender;
import com.starwars.resistancesocialnetwork.domains.enums.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< Updated upstream
import java.util.EnumMap;
=======
import java.util.List;
>>>>>>> Stashed changes

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rebel {
    private Long id;
    private String name;
    private Integer age;
    private Gender gender;
    private Long headquarterId;
    private EnumMap<Item, Integer> inventory;
}
