package com.starwars.resistancesocialnetwork.gateways.controllers.request;

import com.starwars.resistancesocialnetwork.domains.enums.Gender;
import com.starwars.resistancesocialnetwork.domains.enums.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RebelRequest {
    private Long id;
    @NotEmpty
    @Size(min = 2, max = 50)
    private String name;
    @Min(value = 18, message = "The rebel must not be underage")
    private Integer age;
    @NotNull(message = "Gender is required")
    private Gender gender;
    @NotNull(message = "The id from the rebel's hq is required!")
    private Long headquarterId;
    @NotNull(message = "The inventory must be informed")
    private List<Item> inventory;
}
