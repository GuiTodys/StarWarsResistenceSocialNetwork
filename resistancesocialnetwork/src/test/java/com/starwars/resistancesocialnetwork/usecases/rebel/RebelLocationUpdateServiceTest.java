package com.starwars.resistancesocialnetwork.usecases.rebel;

import com.starwars.resistancesocialnetwork.builders.domain.HeadQuarterDomainBuilder;
import com.starwars.resistancesocialnetwork.builders.domain.RebelDomainBuilder;
import com.starwars.resistancesocialnetwork.domains.Headquarter;
import com.starwars.resistancesocialnetwork.domains.Rebel;
import com.starwars.resistancesocialnetwork.exceptions.HeadquarterValidationException;
import com.starwars.resistancesocialnetwork.gateways.persistance.HeadQuartersPersistenceGateway;
import com.starwars.resistancesocialnetwork.gateways.persistance.RebelPersistenceGateway;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class RebelLocationUpdateServiceTest {

    @Mock
    RebelGetService rebelGetService;
    @Mock
    HeadQuartersPersistenceGateway headQuartersPersistence;
    @Mock
    RebelPersistenceGateway rebelPersistence;
    @Mock
    Validator validator;

    @InjectMocks
    RebelLocationUpdateService rebelLocationUpdateService;

    @Test
    void execute_when_informed_valid_id_and_headquarter_then_update_location() {
        //given
        Rebel rebel = RebelDomainBuilder.builder().build().toDomain();
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().id(5L).build().toDomain();
        Rebel expectedRebel = RebelDomainBuilder.builder()
                .headquarterId(headquarter.getId()).build().toDomain();
        //when
        Mockito.when(headQuartersPersistence.findByName(headquarter.getName()))
                .thenReturn(Optional.of(headquarter));
        Mockito.when(rebelGetService.getById(rebel.getId()))
                .thenReturn(rebel);
        Mockito.when(rebelPersistence.save(rebel,headquarter))
                .thenReturn(expectedRebel);
        //then
        Rebel result = rebelLocationUpdateService.execute(rebel.getId(), headquarter);
        Assertions.assertThat(result).isEqualTo(expectedRebel);
    }

    @Test
    void execute_when_informed_valid_id_and_new_headquarter_then_create_and_update() {
        //given
        Rebel rebel = RebelDomainBuilder.builder().build().toDomain();
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().id(5L).build().toDomain();
        Rebel expectedRebel = RebelDomainBuilder.builder()
                .headquarterId(headquarter.getId()).build().toDomain();
        Set<ConstraintViolation<Headquarter>> expectedViolations = Collections.emptySet();

        //when
        Mockito.when(headQuartersPersistence.findByName(headquarter.getName()))
                .thenReturn(Optional.empty());
        Mockito.when(headQuartersPersistence.save(headquarter))
                .thenReturn(headquarter);
        Mockito.when(rebelGetService.getById(rebel.getId()))
                .thenReturn(rebel);
        Mockito.when(rebelPersistence.save(rebel,headquarter))
                .thenReturn(expectedRebel);
        Mockito.when(validator.validate(headquarter)).thenReturn(expectedViolations);

        //then
        Rebel result = rebelLocationUpdateService.execute(rebel.getId(), headquarter);
        Assertions.assertThat(result).isEqualTo(expectedRebel);
    }

    @Test
    void execute_when_informed_valid_id_and_invalid_headquarter_then_throws_exception() {
        //given
        Rebel rebel = RebelDomainBuilder.builder().build().toDomain();
        Headquarter headquarter = HeadQuarterDomainBuilder.builder().name("").build().toDomain();

        //when
        Mockito.when(headQuartersPersistence.findByName(headquarter.getName()))
                .thenReturn(Optional.empty());
        Mockito.when(rebelGetService.getById(rebel.getId()))
                .thenReturn(rebel);
        Mockito.doThrow(HeadquarterValidationException.class).when(validator).validate(headquarter);

        //then
        assertThrows(HeadquarterValidationException.class, () -> rebelLocationUpdateService.execute(rebel.getId(), headquarter));
    }
}