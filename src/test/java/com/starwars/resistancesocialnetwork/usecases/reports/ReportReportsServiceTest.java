package com.starwars.resistancesocialnetwork.usecases.reports;

import com.starwars.resistancesocialnetwork.builders.domain.RebelDomainBuilder;
import com.starwars.resistancesocialnetwork.builders.domain.ReportsDomainBuilder;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.domains.Reports;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReportReportsServiceTest {

    @Mock
    RebelGetService rebelGetService;
    @InjectMocks
    ReportReportsService reportReportsService;

    @Test
    void execute_return_a_reports_object_with_metrics() {
        Rebel firtRebel = RebelDomainBuilder.builder().build().toDomain();
        Rebel secondRebel = RebelDomainBuilder.builder().traitor(true).build().toDomain();
        secondRebel.setInventory(List.of(Item.WEAPON));
        List<Rebel> rebels = List.of(firtRebel,secondRebel);
        Reports expectedReports = ReportsDomainBuilder.builder()
                .notTraitorsPercentage(50f)
                .traitorsPercentage(50f)
                .traitorsPoints(4)
                .build().toDomain();
        //when
        Mockito.when(rebelGetService.getAll()).thenReturn(rebels);

        //then
        Reports result = reportReportsService.execute();

        Assertions.assertThat(result).isEqualTo(expectedReports);


    }
}