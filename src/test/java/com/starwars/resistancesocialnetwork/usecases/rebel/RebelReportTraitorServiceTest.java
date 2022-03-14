package com.starwars.resistancesocialnetwork.usecases.rebel;

import com.starwars.resistancesocialnetwork.builders.domain.HeadQuarterDomainBuilder;
import com.starwars.resistancesocialnetwork.builders.domain.RebelDomainBuilder;
import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.gateways.persistance.RebelPersistenceGateway;
import com.starwars.resistancesocialnetwork.usecases.headquarter.HeadquarterGetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RebelReportTraitorServiceTest {
    @Mock
    private RebelPersistenceGateway rebelPersistence;
    @Mock
    private RebelGetService rebelGetService;
    @Mock
    private HeadquarterGetService headquarterGetService;
    @InjectMocks
    private RebelReportTraitorService rebelReportTraitorService;

    @Test
    void execute_when_inform_a_valid_rebel_then_report() {
        //given
        Rebel rebel = RebelDomainBuilder.builder().build().toDomain();
        Rebel expectedReported = RebelDomainBuilder.builder().reports(1).build().toDomain();
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().build().toDomain();

        //when
        Mockito.when(rebelGetService.getById(rebel.getId()))
                .thenReturn(rebel);
        Mockito.when(headquarterGetService.getById(rebel.getHeadquarterId()))
                .thenReturn(headquarter);
        Mockito.when(rebelPersistence.save(rebel,headquarter))
                .thenReturn(rebel);
        //then
        Rebel result = rebelReportTraitorService.execute(rebel.getId());

        org.assertj.core.api.Assertions.assertThat(result)
                .isEqualTo(expectedReported);


    }
}