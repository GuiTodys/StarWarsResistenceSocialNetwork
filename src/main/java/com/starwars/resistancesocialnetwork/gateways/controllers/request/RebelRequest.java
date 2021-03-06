package com.starwars.resistancesocialnetwork.gateways.controllers.request;

import com.starwars.resistancesocialnetwork.domains.enums.Gender;
import com.starwars.resistancesocialnetwork.domains.enums.Item;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RebelRequest {
    @NotEmpty
    @Size(min = 2, max = 50)
    @ApiModelProperty(required = true, value="Rebel's name", example = "Luke")
    private String name;
    @Min(value = 18, message = "The rebel must not be underage")
    @ApiModelProperty(required = true, value="Rebel's age", example = "18")
    private Integer age;
    @NotNull(message = "Gender is required")
    @ApiModelProperty(required = true, value="Rebel's gender")
    private Gender gender;
    @NotNull(message = "The id from the rebel's hq is required!")
    private Long headquarterId;
    @NotNull(message = "The inventory must be informed")
    @ApiModelProperty(required = true, value="Rebel's inventory list")
    private List<Item> inventory;
}
