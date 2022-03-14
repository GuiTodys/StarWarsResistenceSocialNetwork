package com.starwars.resistancesocialnetwork.usecases.reports;

import com.starwars.resistancesocialnetwork.builders.domain.ItemsPerRebelDomainBuilder;
import com.starwars.resistancesocialnetwork.builders.domain.RebelDomainBuilder;
import com.starwars.resistancesocialnetwork.domains.ItemsPerRebel;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.domains.enums.Item;
import com.starwars.resistancesocialnetwork.usecases.rebel.RebelGetService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class ReportItemsPerRebelServiceTest {

    @Mock
    RebelGetService rebelGetService;

    @InjectMocks
    ReportItemsPerRebelService reportItemsPerRebelService;

    @Test
    void execute_return_an_items_per_rebel_object_with_metrics() {
        //given
        Rebel firtRebel = RebelDomainBuilder.builder().build().toDomain();
        firtRebel.setInventory(List.of(Item.FOOD,Item.FOOD,Item.FOOD));
        Rebel secondRebel = RebelDomainBuilder.builder().build().toDomain();
        secondRebel.setInventory(List.of(Item.WEAPON,Item.FOOD));
        List<Rebel> rebels = List.of(firtRebel,secondRebel);
        ItemsPerRebel expectedResult = ItemsPerRebelDomainBuilder.builder()
                .ammoPerRebel(0f)
                .waterPerRebel(0f)
                .foodPerRebel(2f)
                .weaponsPerRebel(0.5f)
                .build().toDomain();

        //when
        Mockito.when(rebelGetService.getAll()).thenReturn(rebels);

        //then
        ItemsPerRebel result = reportItemsPerRebelService.execute();

        Assertions.assertThat(result).isEqualTo(expectedResult);

    }
}